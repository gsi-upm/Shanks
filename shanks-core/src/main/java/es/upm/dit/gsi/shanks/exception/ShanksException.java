/*******************************************************************************
 * Copyright  (C) 2014 Álvaro Carrera Barroso
 * Grupo de Sistemas Inteligentes - Universidad Politecnica de Madrid
 *  
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *  
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
/**
 * es.upm.dit.gsi.shanks
 * 02/04/2012
 */
package es.upm.dit.gsi.shanks.exception;

/**
 * This exception include all possible exceptions that can be generated on the
 * hole simulation framework. It is intended to unify all throws exception
 * declaration to make statements more legible.
 * 
 * @author darofar
 * 
 */
public class ShanksException extends Exception {

    /**
     * default constructor.
     */
    public ShanksException() {
        super();
    }

    /**
     * This have to bee the more used constructor, cause a ShanksException its
     * really whole prosibilitie of other exceptions. So when need to create one
     * Shanks exception it is necessary to pass to the new exception object all
     * fields and characteristic from the original exception.
     * 
     * @param message
     *            the message of the original exception.
     * @param cause
     *            the original exception object.
     */
    public ShanksException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor that just acquire the original message.
     * 
     * @param message
     *            the message of the original exception.
     */
    public ShanksException(String message) {
        super(message);
    }

    /**
     * Constructor that just acquire the original throwable object.
     * 
     * @param cause
     *            the original exception object.
     */
    public ShanksException(Throwable cause) {
        super(cause);
    }

    private static final long serialVersionUID = -3745231676898724783L;

}
