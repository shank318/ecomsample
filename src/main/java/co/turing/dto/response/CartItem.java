package co.turing.dto.response;

import lombok.Data;

public interface CartItem {

    public int getItemId();

    public String getName();

    public String getAttributes() ;

    public int getProductId();

    public double getPrice();

    public int getQuantity();

    public String getImage();

    public double getSubtotal();


}
