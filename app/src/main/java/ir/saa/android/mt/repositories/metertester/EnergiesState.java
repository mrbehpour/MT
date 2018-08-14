package ir.saa.android.mt.repositories.metertester;

public class EnergiesState {
    public Boolean energy1AState;
    public Boolean energy1RState;
    public Boolean energy2AState;
    public Boolean energy2RState;
    public Boolean energy3AState;
    public Boolean energy3RState;

    public EnergiesState(Boolean _energy1AState, Boolean _energy1RState,
                         Boolean _energy2AState, Boolean _energy2RState,
                         Boolean _energy3AState, Boolean _energy3RState){

        energy1AState = _energy1AState;
        energy1RState = _energy1RState;
        energy2AState = _energy2AState;
        energy2RState = _energy2RState;
        energy3AState = _energy3AState;
        energy3RState = _energy2RState;
    }

    public EnergiesState(double _energy1A, double _energy1R,
                         double _energy2A, double _energy2R,
                         double _energy3A, double _energy3R){

        energy1AState = _energy1A>0?true:false;
        energy1RState = _energy1R>0?true:false;
        energy2AState = _energy2A>0?true:false;
        energy2RState = _energy2R>0?true:false;
        energy3AState = _energy3A>0?true:false;
        energy3RState = _energy3R>0?true:false;
    }
}
