package i2.application.bisonfute.demo;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a452457 on 10/12/2015.
 */
public class RandomPointsProvider {

    public static List<MarkerOptions> getRandomBordeauxPoints() {
        List<MarkerOptions> liste = new ArrayList<>();
        BitmapDescriptor iconWork =
                BitmapDescriptorFactory.fromResource(R.drawable.ic_working);
        BitmapDescriptor iconAccident =
                BitmapDescriptorFactory.fromResource(R.drawable.ic_bouchons);
        BitmapDescriptor iconIncident =
                BitmapDescriptorFactory.fromResource(R.drawable.ic_incident);

        liste.add(new MarkerOptions()
                .position(new LatLng(44.8417531, -0.5691530999999941))
                .title("Miroir d'eau")
                .snippet("Miroir d'eau de bordeaux")
                .icon(iconAccident));


        liste.add(new MarkerOptions()
                        .position(new LatLng(44.844469, -0.5737920000000258))
                        .title("Quinconces")
                        .snippet("Place des quinconces")
                        .icon(iconWork)
        );
        liste.add(new MarkerOptions()
                        .position(new LatLng(44.8268502, -0.5560778999999911))
                        .title("Gare Saint Jean")
                        .snippet("Gare de Bordeaux")
                        .icon(iconIncident)
        );

        liste.add(new MarkerOptions()
                        .position(new LatLng(44.83731700000001, -0.5771620000000439))
                        .title("Hôtel de Ville")
                        .snippet("Hote de ville de Bordeaux")
                        .icon(iconAccident)
        );
        liste.add(new MarkerOptions()
                        .position(new LatLng(44.830013, -0.5963850000000548))
                        .title("Stade Chaban Delmas")
                        .snippet("Stade de Bordeaux")
                        .icon(iconIncident)
        );
        //RANDOM :
        liste.add(new MarkerOptions()
                        .position(new LatLng(44.8333179, -0.59014595))
                        .title("RANDOM")
                        .snippet("RANDOM")
                        .icon(iconAccident)
        );
        liste.add(new MarkerOptions()
                        .position(new LatLng(44.82999928, -0.57605697))
                        .title("RANDOM")
                        .snippet("RANDOM")
                        .icon(iconWork)
        );
        liste.add(new MarkerOptions()
                        .position(new LatLng(44.83169781, -0.58367563))
                        .title("RANDOM")
                        .snippet("RANDOM")
                        .icon(iconIncident)
        );
        liste.add(new MarkerOptions()
                        .position(new LatLng(44.84108296, -0.58406962))
                        .title("RANDOM")
                        .snippet("RANDOM")
                        .icon(iconAccident)
        );
        liste.add(new MarkerOptions()
                        .position(new LatLng(44.84215953, -0.58366404))
                        .title("RANDOM")
                        .snippet("RANDOM")
                        .icon(iconWork)
        );
        liste.add(new MarkerOptions()
                        .position(new LatLng(44.84050943, -0.58949019))
                        .title("RANDOM")
                        .snippet("RANDOM")
                        .icon(iconIncident)
        );
        liste.add(new MarkerOptions()
                        .position(new LatLng(44.83369931, -0.5711256))
                        .title("RANDOM")
                        .snippet("RANDOM")
                        .icon(iconAccident)
        );
        liste.add(new MarkerOptions()
                        .position(new LatLng(44.84203358, -0.58623078))
                        .title("RANDOM")
                        .snippet("RANDOM")
                        .icon(iconWork)
        );
        liste.add(new MarkerOptions()
                        .position(new LatLng(44.83932668, -0.57950108))
                        .title("RANDOM")
                        .snippet("RANDOM")
                        .icon(iconIncident)
        );
        return liste;
    }


}
