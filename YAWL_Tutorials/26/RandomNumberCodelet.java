/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yawlfoundation.yawl.resourcing.codelets;;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jdom2.Element;
import org.yawlfoundation.yawl.elements.data.YParameter;

/**
 * A simple codelet example demonstrating usage. Much of the work is done in the
 * abstract ancestor, but of course those base classes do not need to be relied upon -
 * all the work my be done in this class if desired.
 *
 * The Element passed back to the caller of the execute method must contain the results
 * of the execution. It should be constructed as:
 *      <root>
 *         <paramName>paramValue</paramName>
 *         <paramName>paramValue</paramName>
 *         ......
 *      </root>
 *
 * "root" can be called anything; each paramName must be the same as one of the
 * triggering workitem's output parameters. 
 *
 * Creation Date: 20/02/2017
 */
public class RandomNumberCodelet extends AbstractCodelet {

	public RandomNumberCodelet(){
		super();
		setDescription("This codelet returns a random number, its maximum being the input int.<br> " +
                "Required parameters:<br> " +
                "Input: max (int) - maximum value of randomized number<br>" +
                "Output: r (int)");
	}
	
	/**
     * The implentation of the abstact class that does the work of this codelet. Note
     * that calls to most of the base class methods may throw a CodeletExecutionException
     * which should be passed back to the caller,
     *
     * @param inData The calling workitem's data
     * @param inParams the workitem's input parameters
     * @param outParams the workitem's output parameters
     * @return the result of the codelet's work as a JDOM Element
     * @throws org.yawlfoundation.yawl.resourcing.codelets.CodeletExecutionException
     */
	public Element execute(Element inData, List<YParameter> inParams,
			List<YParameter> outParams) throws CodeletExecutionException {
		// set the inputs passed in the base class
        setInputs(inData, inParams, outParams);
		
		int max;
		
		try{
			max = Integer.parseInt((String) getParameterValue("max"));
		}
		catch (ClassCastException cce) {
            throw new CodeletExecutionException("Exception casting input values to " +
                                                "int types.") ;
        }
		
		
		//create a random number between 1 and max (input)
		Random generator = new Random();
		int r = generator.nextInt(max) + 1;
		
		//set the value for the output parameter
		setParameterValue("r", String.valueOf(r));
		
		return getOutputData();
	}

	
	public void cancel(){}
	
	public List<YParameter> getRequiredParams() {
		List<YParameter> params = new ArrayList<YParameter>();

        YParameter param = new YParameter(null, YParameter._INPUT_PARAM_TYPE);
        param.setDataTypeAndName("int", "max", XSD_NAMESPACE);
        param.setDocumentation("maximum random number");
        params.add(param);

        param = new YParameter(null, YParameter._OUTPUT_PARAM_TYPE);
        param.setDataTypeAndName("int", "r", XSD_NAMESPACE);
        param.setDocumentation("randomized number");
        params.add(param);
        return params;
    }
}
