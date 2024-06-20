package Lk.ijse.Dress.DTO;


public class Customer {
    private String Customer_Id;
    private String Customer_name;
    private String Customer_Address;
    private int Customer_contact_Number;

    private String email;

    public String getCustomer_Id() {
        return Customer_Id;
    }

    public void setCustomer_Id(String customer_Id) {
        Customer_Id = customer_Id;
    }

    public String getCustomer_name() {
        return Customer_name;
    }

    public void setCustomer_name(String customer_name) {
        Customer_name = customer_name;
    }

    public String getCustomer_Address() {
        return Customer_Address;
    }

    public void setCustomer_Address(String customer_Address) {
        Customer_Address = customer_Address;
    }

    public int getCustomer_contact_Number() {
        return Customer_contact_Number;
    }

    public void setCustomer_contact_Number(int customer_contact_Number) {
        Customer_contact_Number = customer_contact_Number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Customer(String customer_Id, String customer_name, String customer_Address, int customer_contact_Number, String email) {
        Customer_Id = customer_Id;
        Customer_name = customer_name;
        Customer_Address = customer_Address;
        Customer_contact_Number = customer_contact_Number;
        this.email = email;
    }

    public Customer() {
    }

    @Override
    public String toString() {
        return "Customer{" +
                "Customer_Id='" + Customer_Id + '\'' +
                ", Customer_name='" + Customer_name + '\'' +
                ", Customer_Address='" + Customer_Address + '\'' +
                ", Customer_contact_Number=" + Customer_contact_Number +
                ", email='" + email + '\'' +
                '}';
    }

}
