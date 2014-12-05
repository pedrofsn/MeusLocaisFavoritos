package br.pedrofsn.meuslocaisfavoritos.interfaces;

import br.pedrofsn.meuslocaisfavoritos.model.directions.DirectionResponse;

/**
 * Created by pedrofsn on 04/12/2014.
 */
public interface IAsyncTaskConsultaDirection {

    public void setDirection(DirectionResponse directionResponse);
    public void processandoAsyncTaskDirection();

}
