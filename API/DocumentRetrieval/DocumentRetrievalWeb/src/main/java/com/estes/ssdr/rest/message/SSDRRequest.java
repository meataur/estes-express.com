/**
 * @author: Todd Allen
 *
 * Creation date: 04/08/2016
 *
 */

package com.estes.ssdr.rest.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Parent class for DocumentRequest
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
public class SSDRRequest extends DocumentRequest
{
}
