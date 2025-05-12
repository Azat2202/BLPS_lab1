package ru.itmo.lab.kafka;

public class HotelKafkaDTO {
    private Long id;

    private String name;

    private String description;

    private CityKafkaDTO cityKafkaDTO;

    private String address;

    private HotelRatingKafkaDTO rating;


    public HotelKafkaDTO(Long id, String name, String description, CityKafkaDTO cityKafkaDTO, String address, HotelRatingKafkaDTO rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cityKafkaDTO = cityKafkaDTO;
        this.address = address;
        this.rating = rating;
    }

    public HotelKafkaDTO() {
    }

    public static HotelKafkaDTOBuilder builder() {
        return new HotelKafkaDTOBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public CityKafkaDTO getCity() {
        return this.cityKafkaDTO;
    }

    public String getAddress() {
        return this.address;
    }

    public HotelRatingKafkaDTO getRating() {
        return this.rating;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCity(CityKafkaDTO cityKafkaDTO) {
        this.cityKafkaDTO = cityKafkaDTO;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRating(HotelRatingKafkaDTO rating) {
        this.rating = rating;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof HotelKafkaDTO)) return false;
        final HotelKafkaDTO other = (HotelKafkaDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        final Object this$city = this.getCity();
        final Object other$city = other.getCity();
        if (this$city == null ? other$city != null : !this$city.equals(other$city)) return false;
        final Object this$address = this.getAddress();
        final Object other$address = other.getAddress();
        if (this$address == null ? other$address != null : !this$address.equals(other$address)) return false;
        final Object this$rating = this.getRating();
        final Object other$rating = other.getRating();
        if (this$rating == null ? other$rating != null : !this$rating.equals(other$rating)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof HotelKafkaDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $city = this.getCity();
        result = result * PRIME + ($city == null ? 43 : $city.hashCode());
        final Object $address = this.getAddress();
        result = result * PRIME + ($address == null ? 43 : $address.hashCode());
        final Object $rating = this.getRating();
        result = result * PRIME + ($rating == null ? 43 : $rating.hashCode());
        return result;
    }

    public String toString() {
        return "HotelKafkaDTO(id=" + this.getId() + ", name=" + this.getName() + ", description=" + this.getDescription() + ", city=" + this.getCity() + ", address=" + this.getAddress() + ", rating=" + this.getRating() + ")";
    }

    public static class HotelKafkaDTOBuilder {
        private Long id;
        private String name;
        private String description;
        private CityKafkaDTO cityKafkaDTO;
        private String address;
        private HotelRatingKafkaDTO rating;

        HotelKafkaDTOBuilder() {
        }

        public HotelKafkaDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public HotelKafkaDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public HotelKafkaDTOBuilder description(String description) {
            this.description = description;
            return this;
        }

        public HotelKafkaDTOBuilder city(CityKafkaDTO cityKafkaDTO) {
            this.cityKafkaDTO = cityKafkaDTO;
            return this;
        }

        public HotelKafkaDTOBuilder address(String address) {
            this.address = address;
            return this;
        }

        public HotelKafkaDTOBuilder rating(HotelRatingKafkaDTO rating) {
            this.rating = rating;
            return this;
        }

        public HotelKafkaDTO build() {
            return new HotelKafkaDTO(this.id, this.name, this.description, this.cityKafkaDTO, this.address, this.rating);
        }

        public String toString() {
            return "HotelKafkaDTO.HotelKafkaDTOBuilder(id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", city=" + this.cityKafkaDTO + ", address=" + this.address + ", rating=" + this.rating + ")";
        }
    }
}
