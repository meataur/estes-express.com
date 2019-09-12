/**
 * @author: Todd Allen
 *
 * Creation date: 03/26/2019
 */

package com.estes.myestes.rating.dto;

/**
 * E-mail/PDF template for rate quote info
 */
public class QuoteTemplate
{
	public static final String EMAIL_QUOTE = "sendEmail";
	public static final String FAX_QUOTE = "sendFax";
	public static final String PRINT_QUOTE = "printQuote";

	private String app;
	private String operation;
	private String estesLogoPath4Print;
	private String welcomeMessage;
	private RateQuote quote;
	private Terminal origin;
	private Terminal dest;
	private String footerDisclaimer;

	public QuoteTemplate()
	{
	}

	public String getApp()
	{
		return app;
	}

	public Terminal getDest()
	{
		return dest;
	}

	public String getFooterDisclaimer()
	{
		return footerDisclaimer;
	}

	public String getEstesLogoPath4Print()
	{
		return estesLogoPath4Print;
	}

	public String getOperation()
	{
		return operation;
	}

	public Terminal getOrigin()
	{
		return origin;
	}

	public RateQuote getQuote()
	{
		return quote;
	}

	public String getWelcomeMessage()
	{
		return welcomeMessage;
	}

	public void setApp(String appType)
	{
		this.app = appType;
	}

	public void setDest(Terminal dest)
	{
		this.dest = dest;
	}

	public void setFooterDisclaimer(String disclaimer)
	{
		this.footerDisclaimer = disclaimer;
	}

	public void setEstesLogoPath4Print(String path)
	{
		this.estesLogoPath4Print = path;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public void setOrigin(Terminal org)
	{
		this.origin = org;
	}

	public void setQuote(RateQuote quote)
	{
		this.quote = quote;
	}

	public void setWelcomeMessage(String message)
	{
		this.welcomeMessage = message;
	}
}
