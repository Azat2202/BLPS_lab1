package ru.itmo.lab.kafka;

import java.time.LocalDate;

public class BookingKafkaDTO {
    private Long id;

    private UserKafkaDTO user;

    private LocalDate startDate;

    private LocalDate endDate;

    private RoomKafkaDTO room;

    private BookingStatusKafkaDTO status;

    public BookingKafkaDTO() {
    }

    public BookingKafkaDTO(Long id, UserKafkaDTO user, LocalDate startDate, LocalDate endDate, RoomKafkaDTO room, BookingStatusKafkaDTO status) {
        this.id = id;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.room = room;
        this.status = status;
    }

    public static BookingKafkaDTOBuilder builder() {
        return new BookingKafkaDTOBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public UserKafkaDTO getUser() {
        return this.user;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public RoomKafkaDTO getRoom() {
        return this.room;
    }

    public BookingStatusKafkaDTO getStatus() {
        return this.status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(UserKafkaDTO user) {
        this.user = user;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setRoom(RoomKafkaDTO room) {
        this.room = room;
    }

    public void setStatus(BookingStatusKafkaDTO status) {
        this.status = status;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof BookingKafkaDTO)) return false;
        final BookingKafkaDTO other = (BookingKafkaDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$user = this.getUser();
        final Object other$user = other.getUser();
        if (this$user == null ? other$user != null : !this$user.equals(other$user)) return false;
        final Object this$startDate = this.getStartDate();
        final Object other$startDate = other.getStartDate();
        if (this$startDate == null ? other$startDate != null : !this$startDate.equals(other$startDate)) return false;
        final Object this$endDate = this.getEndDate();
        final Object other$endDate = other.getEndDate();
        if (this$endDate == null ? other$endDate != null : !this$endDate.equals(other$endDate)) return false;
        final Object this$room = this.getRoom();
        final Object other$room = other.getRoom();
        if (this$room == null ? other$room != null : !this$room.equals(other$room)) return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BookingKafkaDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $user = this.getUser();
        result = result * PRIME + ($user == null ? 43 : $user.hashCode());
        final Object $startDate = this.getStartDate();
        result = result * PRIME + ($startDate == null ? 43 : $startDate.hashCode());
        final Object $endDate = this.getEndDate();
        result = result * PRIME + ($endDate == null ? 43 : $endDate.hashCode());
        final Object $room = this.getRoom();
        result = result * PRIME + ($room == null ? 43 : $room.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        return result;
    }

    public String toString() {
        return "BookingKafkaDTO(id=" + this.getId() + ", user=" + this.getUser() + ", startDate=" + this.getStartDate() + ", endDate=" + this.getEndDate() + ", room=" + this.getRoom() + ", status=" + this.getStatus() + ")";
    }

    public static class BookingKafkaDTOBuilder {
        private Long id;
        private UserKafkaDTO user;
        private LocalDate startDate;
        private LocalDate endDate;
        private RoomKafkaDTO room;
        private BookingStatusKafkaDTO status;

        BookingKafkaDTOBuilder() {
        }


        public BookingKafkaDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public BookingKafkaDTOBuilder user(UserKafkaDTO user) {
            this.user = user;
            return this;
        }

        public BookingKafkaDTOBuilder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public BookingKafkaDTOBuilder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public BookingKafkaDTOBuilder room(RoomKafkaDTO room) {
            this.room = room;
            return this;
        }

        public BookingKafkaDTOBuilder status(BookingStatusKafkaDTO status) {
            this.status = status;
            return this;
        }

        public BookingKafkaDTO build() {
            return new BookingKafkaDTO(this.id, this.user, this.startDate, this.endDate, this.room, this.status);
        }

        public String toString() {
            return "BookingKafkaDTO.BookingKafkaDTOBuilder(id=" + this.id + ", user=" + this.user + ", startDate=" + this.startDate + ", endDate=" + this.endDate + ", room=" + this.room + ", status=" + this.status + ")";
        }
    }
}

