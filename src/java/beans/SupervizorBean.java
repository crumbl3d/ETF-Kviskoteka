/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author crumbl3d
 */
@ManagedBean
@SessionScoped
@Named(value = "SupervizorBean")
public class SupervizorBean implements Serializable {

    /**
     * Creates a new instance of SupervizorBean
     */
    public SupervizorBean() {
    }

}