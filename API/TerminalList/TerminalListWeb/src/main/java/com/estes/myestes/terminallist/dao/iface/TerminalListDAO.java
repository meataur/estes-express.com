/**
 * @author: shelender singh
 *
 * Creation date: 09/13/2018
 */

package com.estes.myestes.terminallist.dao.iface;

import java.util.List;

import com.estes.myestes.terminallist.dto.EmailRequestDTO;
import com.estes.myestes.terminallist.dto.Terminal;

public interface TerminalListDAO 
{
	/**
	 * Search for servicing terminals based on request
	 * 
	 * @param dto Request info
	 * @return List of terminals
	 */
	List<Terminal> searchTerminals(EmailRequestDTO dto);
}
