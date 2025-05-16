package ru.itmo.lab.kafka;

public class RoomKafkaDTO {
    private Long id;

    private String name;

    private Long capacity;

    private Integer price;

    private HotelKafkaDTO hotel;


    public RoomKafkaDTO(Long id, String name, Long capacity, Integer price, HotelKafkaDTO hotel) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.price = price;
        this.hotel = hotel;
    }

    public RoomKafkaDTO() {
    }

    public static RoomKafkaDTOBuilder builder() {
        return new RoomKafkaDTOBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Long getCapacity() {
        return this.capacity;
    }

    public Integer getPrice() {
        return this.price;
    }

    public HotelKafkaDTO getHotel() {
        return this.hotel;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setHotel(HotelKafkaDTO hotel) {
        this.hotel = hotel;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof RoomKafkaDTO)) return false;
        final RoomKafkaDTO other = (RoomKafkaDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$capacity = this.getCapacity();
        final Object other$capacity = other.getCapacity();
        if (this$capacity == null ? other$capacity != null : !this$capacity.equals(other$capacity)) return false;
        final Object this$price = this.getPrice();
        final Object other$price = other.getPrice();
        if (this$price == null ? other$price != null : !this$price.equals(other$price)) return false;
        final Object this$hotel = this.getHotel();
        final Object other$hotel = other.getHotel();
        if (this$hotel == null ? other$hotel != null : !this$hotel.equals(other$hotel)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof RoomKafkaDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $capacity = this.getCapacity();
        result = result * PRIME + ($capacity == null ? 43 : $capacity.hashCode());
        final Object $price = this.getPrice();
        result = result * PRIME + ($price == null ? 43 : $price.hashCode());
        final Object $hotel = this.getHotel();
        result = result * PRIME + ($hotel == null ? 43 : $hotel.hashCode());
        return result;
    }

    public String toString() {
        return "RoomKafkaDTO(id=" + this.getId() + ", name=" + this.getName() + ", capacity=" + this.getCapacity() + ", price=" + this.getPrice() + ", hotel=" + this.getHotel() + ")";
    }

    public static class RoomKafkaDTOBuilder {
        private Long id;
        private String name;
        private Long capacity;
        private Integer price;
        private HotelKafkaDTO hotel;

        RoomKafkaDTOBuilder() {
        }

        public RoomKafkaDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public RoomKafkaDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public RoomKafkaDTOBuilder capacity(Long capacity) {
            this.capacity = capacity;
            return this;
        }

        public RoomKafkaDTOBuilder price(Integer price) {
            this.price = price;
            return this;
        }

        public RoomKafkaDTOBuilder hotel(HotelKafkaDTO hotel) {
            this.hotel = hotel;
            return this;
        }

        public RoomKafkaDTO build() {
            return new RoomKafkaDTO(this.id, this.name, this.capacity, this.price, this.hotel);
        }

        public String toString() {
            return "RoomKafkaDTO.RoomKafkaDTOBuilder(id=" + this.id + ", name=" + this.name + ", capacity=" + this.capacity + ", price=" + this.price + ", hotel=" + this.hotel + ")";
        }
    }
}

