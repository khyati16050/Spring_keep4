package com.stackroute.keepnote.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/*
 * Please note that this class is annotated with @Document annotation
 * @Document identifies a domain object to be persisted to MongoDB.
 *  */
@Document
public class Category {

	/*
	 * This class should have five fields
	 * (categoryId,categoryName,categoryDescription,
	 * categoryCreatedBy,categoryCreationDate). Out of these five fields, the field
	 * categoryId should be annotated with @Id. This class should also contain the
	 * getters and setters for the fields along with the no-arg , parameterized
	 * constructor and toString method. The value of categoryCreationDate should not
	 * be accepted from the user but should be always initialized with the system
	 * date. 
	 */
    @Id
	private String categoryId;
	private String categpryName;
	private String categoryDescription;
	private String categoryCreatedBy;
	private Date categoryCreationDate;

	public Category(){

    }
    public Category(String id,String name,String des,String created, Date date)
    {
        this.categoryId = id;
        this.categpryName = name;
        this.categoryDescription = des;
        this.categoryCreatedBy = created;
        this.categoryCreationDate = date;
    }

    public String getId() {
        return this.categoryId;
    }

    public void setId(String id) {
        this.categoryId = id;
       
    }

    public String getCategoryName() {
        return this.categpryName;
    }

    public void setCategoryName(String categoryName) {
        this.categpryName = categoryName;
       
    }

    public String getCategoryDescription() {
        return this.categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
      this.categoryDescription = categoryDescription;
    }

    public String getCategoryCreatedBy() {
        return this.categoryCreatedBy;
    }

    public void setCategoryCreatedBy(String categoryCreatedBy) {
        this.categoryCreatedBy = categoryCreatedBy;
    }

    public Date getCategoryCreationDate() {
        return this.categoryCreationDate;
    }

    public void setCategoryCreationDate(Date categoryCreationDate) {
        this.categoryCreationDate  = categoryCreationDate;
       
    }


}
