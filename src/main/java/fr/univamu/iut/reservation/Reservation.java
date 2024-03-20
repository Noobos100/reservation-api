package fr.univamu.iut.reservation;

public class Reservation {
    protected String id;
    protected String date;
    protected String reference;

    public Reservation() {
    }

    public Reservation(String id, String date, String reference) {
        this.id = id;
        this.date = date;
        this.reference = reference;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", reference='" + reference + '\'' +
                '}';
    }
}
