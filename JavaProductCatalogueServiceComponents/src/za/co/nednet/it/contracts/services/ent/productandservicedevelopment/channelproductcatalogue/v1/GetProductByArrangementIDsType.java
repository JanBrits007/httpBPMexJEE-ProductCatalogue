//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vIBM 2.2.3-11/28/2011 06:21 AM(foreman)- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.07.13 at 02:48:21 PM CAT 
//


package za.co.nednet.it.contracts.services.ent.productandservicedevelopment.channelproductcatalogue.v1;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetProductType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetProductType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://contracts.it.nednet.co.za/services/ent/productandservicedevelopment/ChannelProductCatalogue/v1}productIdentifier" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetProductByArrangementIDType", propOrder = {
    "productIdentifiers",
    "arrangementIDs"
})
public class GetProductByArrangementIDsType {

    @XmlElement(type = Integer.class)
    protected List<Integer> productIdentifiers;

    @XmlElement(type = String.class)
    protected List<String> arrangementIDs;
    
	public List<String> getArrangementIDs() {
		if(arrangementIDs == null) {
			arrangementIDs = new ArrayList<String>();
		}
		
		return arrangementIDs;
	}

	public List<Integer> getProductIdentifiers() {
		if(productIdentifiers == null) {
			productIdentifiers = new ArrayList<Integer>();
		}
		
		return productIdentifiers;
	}
}
