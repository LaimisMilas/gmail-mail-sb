package lt.gmail.mail.sender.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "company_info")
public class CompanyInfoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String title;
	private String address;
	private String email;
	
	@Column(name="company_code")
	private String companyCode;
	
	@Column(name="raw_contacts")
	private String rawContacts;
	
	@Column(name="company_url")
	private String companyUrl;
	
	private int status;
	
	@Column(name="web_site_url")
	private String webSiteUrl;
	
	@Column(name="company_owner")
	private String companyOwner;
	
	private String sales;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getRawContacts() {
		return rawContacts;
	}
	public void setRawContacts(String rawContacts) {
		this.rawContacts = rawContacts;
	}
	public String getCompanyUrl() {
		return companyUrl;
	}
	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getWebSiteUrl() {
		return webSiteUrl;
	}
	public void setWebSiteUrl(String webSiteUrl) {
		this.webSiteUrl = webSiteUrl;
	}
	public String getCompanyOwner() {
		return companyOwner;
	}
	public void setCompanyOwner(String companyOwner) {
		this.companyOwner = companyOwner;
	}
	public String getSales() {
		return sales;
	}
	public void setSales(String sales) {
		this.sales = sales;
	}

	
	
}
