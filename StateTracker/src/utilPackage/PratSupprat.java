package utilPackage;

public class PratSupprat {
	public PratSupprat(String idPrat, String idSupPrat, String cOD_DATO_AGG, String vAL_DATO_AGG) {
		super();
		IdPrat = idPrat;
		IdSupPrat = idSupPrat;
		COD_DATO_AGG = cOD_DATO_AGG;
		VAL_DATO_AGG = vAL_DATO_AGG;
	}
	
	
	public PratSupprat() {
		super();
		IdPrat = "";
		IdSupPrat = "";
		COD_DATO_AGG = "";
		VAL_DATO_AGG = "";
	}
	
	
	public String IdPrat;
	public String IdSupPrat;
	public String COD_DATO_AGG;
	public String VAL_DATO_AGG;
	public String getIdPrat() {
		return IdPrat;
	}


	public void setIdPrat(String idPrat) {
		IdPrat = idPrat;
	}


	public String getIdSupPrat() {
		return IdSupPrat;
	}


	public void setIdSupPrat(String idSupPrat) {
		IdSupPrat = idSupPrat;
	}


	public String getCOD_DATO_AGG() {
		return COD_DATO_AGG;
	}


	public void setCOD_DATO_AGG(String cOD_DATO_AGG) {
		COD_DATO_AGG = cOD_DATO_AGG;
	}


	public String getVAL_DATO_AGG() {
		return VAL_DATO_AGG;
	}


	public void setVAL_DATO_AGG(String vAL_DATO_AGG) {
		VAL_DATO_AGG = vAL_DATO_AGG;
	}

}
