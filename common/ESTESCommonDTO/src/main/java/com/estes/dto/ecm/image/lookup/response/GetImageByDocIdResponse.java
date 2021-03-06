//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated for use by Estes Express Lines Inc.
//


package com.estes.dto.ecm.image.lookup.response;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.estes.dto.ecm.image.common.QueriedImage;


/**
 * <p>Java class for getImageByDocIdResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getImageByDocIdResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="status" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;whiteSpace value="preserve"/>
 *               &lt;enumeration value="ERROR"/>
 *               &lt;enumeration value="SUCCESS"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="image" type="{http://www.estes-express.com/ECM/imaging/queryImage}queriedImage"/>
 *         &lt;element name="generalErrors" type="{http://www.estes-express.com/ECM/imaging/queryImage}error" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getImageByDocIdResponse", propOrder = {
    "status",
    "image",
    "generalErrors"
})
public class GetImageByDocIdResponse {

    @XmlElement(namespace = "http://www.estes-express.com/ECM/imaging/queryImage", nillable = true)
    protected String status;
    @XmlElement(namespace = "http://www.estes-express.com/ECM/imaging/queryImage", required = true, nillable = true)
    protected QueriedImage image;
    @XmlElement(namespace = "http://www.estes-express.com/ECM/imaging/queryImage", required = true, nillable = true)
    protected List<Error> generalErrors;

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the image property.
     * 
     * @return
     *     possible object is
     *     {@link QueriedImage }
     *     
     */
    public QueriedImage getImage() {
        return image;
    }

    /**
     * Sets the value of the image property.
     * 
     * @param value
     *     allowed object is
     *     {@link QueriedImage }
     *     
     */
    public void setImage(QueriedImage value) {
        this.image = value;
    }

    /**
     * Gets the value of the generalErrors property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the generalErrors property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGeneralErrors().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Error }
     * 
     * 
     */
    public List<Error> getGeneralErrors() {
        if (generalErrors == null) {
            generalErrors = new ArrayList<Error>();
        }
        return this.generalErrors;
    }

}
