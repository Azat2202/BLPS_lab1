/*
 * IronJacamar, a Java EE Connector Architecture implementation
 * Copyright 2013, Red Hat Inc, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package ru.itmo.hotel_tasks.jca;

import org.springframework.web.client.RestTemplate;
import ru.itmo.hotel_tasks.models.Hotel;
import ru.itmo.hotel_tasks.models.Room;
import ru.itmo.hotel_tasks.models.enums.City;
import ru.itmo.hotel_tasks.models.enums.HotelRating;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

/**
 * YandexMapsConnectionImpl
 *
 * @version $Revision: $
 */
public class YandexMapsConnectionImpl implements YandexMapsConnection {
    /**
     * The logger
     */
    private static Logger log = Logger.getLogger(YandexMapsConnectionImpl.class.getName());

    /**
     * ManagedConnection
     */
    private YandexMapsManagedConnection mc;

    /**
     * ManagedConnectionFactory
     */
    private YandexMapsManagedConnectionFactory mcf;
    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String yandexMapsApiKey;
    private static final Random RANDOM = new Random();


    /**
     * Default constructor
     *
     * @param mc  YandexMapsManagedConnection
     * @param mcf YandexMapsManagedConnectionFactory
     */
    public YandexMapsConnectionImpl(YandexMapsManagedConnection mc, YandexMapsManagedConnectionFactory mcf, String apiKey, String yandexMapsApiKey) {
        this.mc = mc;
        this.mcf = mcf;
        this.restTemplate = new RestTemplate();
        this.apiKey = apiKey;
        this.yandexMapsApiKey = yandexMapsApiKey;
    }

    private static final List<String> DISTRICTS_PREPOSITIONAL = List.of(
            "Адмиралтейском",
            "Василеостровском",
            "Выборгском",
            "Калининском",
            "Кировском",
            "Колпинском",
            "Красногвардейском",
            "Красносельском",
            "Кронштадтском",
            "Курортном",
            "Московском",
            "Невском",
            "Петроградском",
            "Приморском",
            "Пушкинском",
            "Фрунзенском",
            "Центральном"
    );

    public static String getRandomDistrict() {
        String base = DISTRICTS_PREPOSITIONAL.get(RANDOM.nextInt(DISTRICTS_PREPOSITIONAL.size()));
        return base + "+районе";
    }

    public List<Hotel> getHotels() {
        String district = getRandomDistrict();
        String url = "https://www.searchapi.io/api/v1/search?api_key=" + apiKey +
                "&check_in_date=2025-06-01&check_out_date=2025-06-03&engine=google_hotels" +
                "&q=Отели+в+Санкт-Петербурге+в+"+ district +"&hl=ru&currency=RUB";
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        List<Map<String, Object>> properties = (List<Map<String, Object>>) response.get("properties");
        LinkedList<Hotel> hotels = new LinkedList<>();
        for (Map<String, Object> hotelProperty : properties) {
            try {
                HotelRating rating;
                Integer ratingLong = 0;
                if (hotelProperty.get("rating") instanceof Double) {
                    ratingLong = Math.toIntExact(Math.round((Double) hotelProperty.get("rating")));
                } else if (hotelProperty.get("rating") instanceof Integer) {
                    ratingLong = (Integer) hotelProperty.get("rating");
                }
                if (ratingLong == 1) {
                    rating = HotelRating.ONE_STAR;
                } else if (ratingLong == 2) {
                    rating = HotelRating.TWO_STAR;
                } else if (ratingLong == 3) {
                    rating = HotelRating.THREE_STAR;
                } else if (ratingLong == 4) {
                    rating = HotelRating.FOUR_STAR;
                } else if (ratingLong == 5) {
                    rating = HotelRating.FIVE_STAR;
                } else
                    rating = HotelRating.NO_RATING;

                Map<String, Object> totalPrice = (Map<String, Object>) hotelProperty.get("total_price");
                Room room = Room.builder()
                        .name("Стандартная комната")
                        .capacity(2L)
                        .price((Integer) totalPrice.get("extracted_price"))
                        .build();

                Map<String, Object> coordinates = (Map<String, Object>) hotelProperty.get("gps_coordinates");
                String address = this.getAddress(
                        (Double) coordinates.get("longitude"),
                        (Double) coordinates.get("latitude")
                );

                String description = (String) hotelProperty.get("description");
                if (description == null)
                    description = "";
                String name = (String) hotelProperty.get("name");
                if (name == null)
                    continue;

                Hotel hotel = Hotel.builder()
                        .name(name)
                        .description(description)
                        .city(City.SAINT_PETERSBURG)
                        .rating(rating)
                        .rooms(List.of(room))
                        .address(address)
                        .build();
                hotels.add(hotel);
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }
        return hotels;
    }

    private String getAddress(Double longitude, Double latitude) {
        String url = String.format(
                "https://geocode-maps.yandex.ru/v1/?apikey=%s&geocode=%f,%f&format=json",
                yandexMapsApiKey, longitude, latitude
        );
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            Map<String, Object> geoResponse = (Map<String, Object>) response.get("response");
            Map<String, Object> geoObjectCollection = (Map<String, Object>) geoResponse.get("GeoObjectCollection");

            List<Map<String, Object>> featureMember = (List<Map<String, Object>>) geoObjectCollection.get("featureMember");

            if (!featureMember.isEmpty()) {
                Map<String, Object> geoObject = (Map<String, Object>) featureMember.get(0).get("GeoObject");
                Map<String, Object> metaDataProperty = (Map<String, Object>) geoObject.get("metaDataProperty");
                Map<String, Object> geocoderMetaData = (Map<String, Object>) metaDataProperty.get("GeocoderMetaData");

                String address = (String) geocoderMetaData.get("text");
                return address;
            }

        } catch (Exception e) {
            System.err.println("Error calling Yandex Maps API: " + e.getMessage());
            return "Санкт-Петербург";
        }
        return "Санкт-Петербург";
    }

    /**
     * Set ManagedConnection
     */
    void setManagedConnection(YandexMapsManagedConnection mc) {
        this.mc = mc;
    }

}
