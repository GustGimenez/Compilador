/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

import java.util.Comparator;

/**
 *
 * @author Gustavo Gimenez
 */
class OrdenaOrdem implements Comparator<Simbolo> {

    @Override
    public int compare(Simbolo o1, Simbolo o2) {
        return o1.getOrdem() - o2.getOrdem();
    }

}
