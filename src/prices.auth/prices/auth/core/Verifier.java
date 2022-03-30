/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prices.auth.core;

/**
 *
 * @author Ichlasul Affan
 */
public interface Verifier {
    AuthPayload verifyAndParse(final String token);
}
