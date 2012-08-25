/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cis726.jsp;

import java.io.*;
import java.util.*;

/**
 *Contains utility methods for JSP page to invoke
 * Helps improve JSP functionality
 * @author GUI Team
 */
public class JSPSupport {

 /**
 *Prints values separated by '#' in  a list
 * 
 * @author GUI Team
 */
public static final String printList(String valueSeparated){
      String value=null;

      StringTokenizer st = new StringTokenizer(valueSeparated, "#");
      StringBuffer sb=new StringBuffer();
      if(st!=null){

    	sb=sb.append("<ul>");
    	while(st.hasMoreTokens()){
            value=st.nextToken().trim();
            sb=sb.append("<li>");
        	       sb=sb.append(value);
            sb=sb.append("</li>");
        }
        sb=sb.append("</ul>");
      }
      return sb.toString();

}

}
