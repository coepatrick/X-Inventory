package com.vaadin.catalogue.backend;

import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.util.Date;

//class that contains a part

public class Part implements Serializable, Cloneable {

    private Long id;

    private String partName = "";
    private String partNumber = "";
    private int quantity = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }
    
    public int getQuantity() {
    	return quantity;
    }
    
    public void setQuantity(int quantity) {
    	this.quantity = quantity;
    }


    @Override
    public Part clone() throws CloneNotSupportedException {
        try {
            return (Part) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", partName=" + partName
                + ", partNumber=" + partNumber + ", quantity=" + quantity + '}';
    }

}
