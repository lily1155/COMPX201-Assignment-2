
public class Appliance{
    private String category;
    private float price;
    private String name;

    public Appliance(String Category, String Name, float Price){
        category = Category;
        name = Name;
        price = Price;
    }
    public String getCategory(){
        return category;
    }
    public float getPrice(){
        return price;
    }
    public String getName(){
        return name;
    }
    //override of toString method that displays the name, category and price in a predetermined format
    @Override public String toString(){
        return name +  "    |    " + category + "    |    $" + price;
    }
    public int compareTo(Appliance other){
        //set 0 as default so it always returns something
        int order = 0;
        order = other.getCategory().compareTo(category);
        //if other is a and category is k, according to java's documentation this code above would return a negative number
        if (order == 0){ //if they're the same, we try to make order the price difference instead
            order = Math.round(other.getPrice() - price);
        }
        if (order == 0){ //if that's also the same surely the names different. if its not, we can detect a duplicate, so cases in the method that calls this should include one for if the result is 0
            order = Math.round(other.getName().compareTo(name));
        }
        return order;
    }
}