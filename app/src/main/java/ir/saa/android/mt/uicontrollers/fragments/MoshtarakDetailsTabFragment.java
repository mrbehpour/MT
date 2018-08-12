package ir.saa.android.mt.uicontrollers.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ir.saa.android.mt.R;
import ir.saa.android.mt.components.TextViewFont;
import ir.saa.android.mt.model.entities.Client;
import ir.saa.android.mt.viewmodels.BazdidViewModel;
import ir.saa.android.mt.viewmodels.MoshtarakDetailsViewModel;

public class MoshtarakDetailsTabFragment extends Fragment
{

    private Long ID = null;
    MoshtarakDetailsViewModel moshtarakDetailsViewModel;

    public MoshtarakDetailsTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ID==null){
            ID=1511753L;
        }
        moshtarakDetailsViewModel =ViewModelProviders.of(getActivity()).get(MoshtarakDetailsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_moshtarak_detail, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        TextView tvShomareBadane=view.findViewById(R.id.tvShomareBadane);
        TextView tvNoeKontor=view.findViewById(R.id.tvNoeKontor);
        TextView tvAmperazh=view.findViewById(R.id.tvAmperazh);
        TextView tvNoeTarefe=view.findViewById(R.id.tvNoeTarefe);
        TextView tvEshterak=view.findViewById(R.id.tvEshterak);
        TextView tvShenasae=view.findViewById(R.id.tvShenasae);
        TextView tvParvandeh=view.findViewById(R.id.tvParvandeh);
        TextView tvRamz=view.findViewById(R.id.tvRamz);
        TextView tvNoeVoltazh=view.findViewById(R.id.tvNoeVoltazh);
        TextView tvGhodrateGharardad=view.findViewById(R.id.tvGhodrateGharardad);
        TextView tvDimandMojaz=view.findViewById(R.id.tvDimandMojaz);
        TextView tvMotavseteMasraf=view.findViewById(R.id.tvMotavseteMasraf);
        TextView tvMasrafeDoreAkhar=view.findViewById(R.id.tvMasrafeDoreAkhar);
        TextView tvKWDoreAkhar=view.findViewById(R.id.tvKWDoreAkhar);
        TextView tvZaribeKontor=view.findViewById(R.id.tvZaribeKontor);
        TextView tvTedadArghamActive=view.findViewById(R.id.tvTedadArghamActive);
        TextView tvTedadArghamReactive=view.findViewById(R.id.tvTedadArghamReactive);
        TextView tvSerialReactive=view.findViewById(R.id.tvSerialReactive);
        TextView tvTarikhEnsheab=view.findViewById(R.id.tvTarikhEnsheab);
        TextView tvTelephone=view.findViewById(R.id.tvTelephone);
        TextView tvCodePosti=view.findViewById(R.id.tvCodePosti);
        TextView tvCodeTarefe=view.findViewById(R.id.tvCodeTarefe);



        moshtarakDetailsViewModel.getDetailsClient(ID).observe(this, new Observer<Client>() {
            @Override
            public void onChanged(@Nullable Client client) {
                if(client!=null) {
                    String MeterTypeName="";
                    switch (client.Faz){
                        case 1:
                            MeterTypeName="تکفاز";
                            break;
                        case 3:
                            MeterTypeName="سه فاز";
                            break;
                    }
                    tvShomareBadane.setText(client.MeterNumActive==null?"":client.MeterNumActive.toString());
                    tvNoeKontor.setText(MeterTypeName);
                    tvAmperazh.setText(client.Amp==null?"": client.Amp.toString());
                    tvNoeTarefe.setText(client.TariffTypeID==null?"": client.TariffTypeID.toString());
                    tvEshterak.setText(client.SubScript==null?"":client.SubScript.toString());
                    tvShenasae.setText(client.CustId==null?"":client.CustId.toString());
                    tvParvandeh.setText(client.FileID==null?"":client.FileID.toString());
                    tvRamz.setText(client.ClientPass==null?"":client.ClientPass.toString());
                    tvNoeVoltazh.setText(client.KindVolt==null?"":client.KindVolt.toString());
                    tvGhodrateGharardad.setText(client.NumContract==null?"":client.NumContract.toString());
                    tvDimandMojaz.setText(client.Demand==null?"":client.Demand.toString());
                    tvMotavseteMasraf.setText(client.UseAvrA==null?"":client.UseAvrA.toString());
                    tvMasrafeDoreAkhar.setText("");
                    tvKWDoreAkhar.setText("");
                    tvZaribeKontor.setText(client.Zarib==null?"":client.Zarib.toString());
                    tvTedadArghamActive.setText(client.NumDigitContor==null?"":client.NumDigitContor.toString());
                    tvTedadArghamReactive.setText("");
                    tvTarikhEnsheab.setText(client.InsDateContor==null?"":client.InsDateContor.toString());
                    tvTelephone.setText(client.Tel==null?"":client.Tel.toString());
                    tvCodePosti.setText(client.PostalCode==null?"":client.PostalCode.toString());
                    tvCodeTarefe.setText(client.TariffTypeID==null?"":client.TariffTypeID.toString());
                }

            }
        });

        super.onViewCreated(view, savedInstanceState);

    }


}
