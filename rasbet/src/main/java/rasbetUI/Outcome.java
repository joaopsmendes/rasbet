package rasbetUI;


public class Outcome {
    String name;
    String price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Outcome{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
