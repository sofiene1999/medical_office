import javafx.beans.property.SimpleStringProperty;

public class Item {

    private SimpleStringProperty name;

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String toString(){

        return getName();

    }

    public Item(String name){

        this.name= new SimpleStringProperty(name);

    }

}
