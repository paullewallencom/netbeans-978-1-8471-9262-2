/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.airalliance;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ejb.Stateless;

/**
 *
 * @author Frank
 */
@WebService()
@Stateless()
public class AAPreProcessor_WS {

/**
     * Web service operation
     */
    @WebMethod(operationName = "areSectorsAvailable")
    public boolean areSectorsAvailable(@WebParam(name = "source")
    String source, @WebParam(name = "destination")
    String destination) {
        
        if(source.equals("BLR") && destination.equals("SFO")){
            return false;
        }
        return true;
    }

}
