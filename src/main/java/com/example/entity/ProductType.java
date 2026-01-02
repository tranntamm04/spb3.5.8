package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

@Getter
@Setter
@Entity
public class ProductType {
   @Id
   private int idType;
   private String nameType;
   private String avt;
   private String description;
   @OneToMany(
      mappedBy = "productType",
      cascade = {CascadeType.ALL}
   )
   @JsonIgnore
   private Set<Product> products;

   public ProductType() {
   }

   public ProductType(int idType, String nameType, String avt, String description, Set<Product> products) {
      this.idType = idType;
      this.nameType = nameType;
      this.avt = avt;
      this.description = description;
      this.products = products;
   }

}
