/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prices.auth.core;

import java.util.List;

/**
 *
 * @author Ichlasul Affan
 */
public interface TokenPayload extends AuthPayload {
    List<String> getAudiences();
    String getIssuer();
}
