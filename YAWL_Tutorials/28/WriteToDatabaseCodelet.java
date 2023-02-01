package org.yawlfoundation.yawl.resourcing.codelets;

import java.sql.SQLException;
import java.util.List;

import org.jdom2.Element;
import org.yawlfoundation.yawl.elements.data.YParameter;
import de.hbrs.yawl.CustomerDAO;


public class WriteToDatabaseCodelet extends AbstractCodelet {

    
    public WriteToDatabaseCodelet() {
		super("Codelet that writes something to a Postgres Database");
	}

	/**
     * Das Codelet wird von YAWL ausgeführt und erhält die nötigen Parameter. Zur Vereinfachung
     * der Parameterbehandlung werden die Hilfsmethoden der abstrakten Elternklasse verwendet,
     *
     * @param inData The calling workitem's data
     * @param inParams the workitem's input parameters
     * @param outParams the workitem's output parameters
     * @return the result of the codelet's work as a JDOM Element
     * @throws CodeletExecutionException
     */    
	@Override
	public Element execute(Element inData, List<YParameter> inParams,
            List<YParameter> outParams) throws CodeletExecutionException {
		
		try {
			// Nötig damit die Hilfsmethoden der Elternklasse funktionieren
			super.setInputs(inData, inParams, outParams);
			// Erstellen unseres Data Access Objects
			CustomerDAO customerDAO = new CustomerDAO();			
			if (super.getParameterValue("customerId") != null && super.getParameterValue("customerId") instanceof String
					&& super.getParameterValue("customerName") != null && super.getParameterValue("customerName") instanceof String) {
				// Auslesen der beiden Parameter
				long customerId = Long.parseLong((String) super.getParameterValue("customerId"));
				String customerName = (String) super.getParameterValue("customerName");
				// Aktualisierung des Datensatzes
				customerDAO.update(customerId, customerName);
			} else {
				// CodeletExecutionException erlaubt kontrollierten Abbruch in YAWL
				throw new CodeletExecutionException("Could not read Input Parameter customerId");
			}
		} catch (SQLException e) {
			throw new CodeletExecutionException(e.getMessage());
		}
		// Methode der Elterklasse liefert korrektes JDOM Element		
		return super.getOutputData();
	}
	
	/**
     * All codelets must provide a mechanism to handle workitem (or case) cancellation.
     * 
     * Here nothing is to do.
     */
	@Override
	public void cancel() {
		//NoOp
	}	

}
