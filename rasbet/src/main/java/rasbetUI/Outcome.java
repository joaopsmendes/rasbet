package rasbetUI;


public class Outcome {
    String name;
    Float price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = Float.parseFloat(price);
    }

    @Override
    public String toString() {
        return "Outcome{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
