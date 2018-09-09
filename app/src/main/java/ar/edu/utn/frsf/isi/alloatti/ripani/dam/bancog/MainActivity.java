package ar.edu.utn.frsf.isi.alloatti.ripani.dam.bancog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.appcompat.R;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;

import ar.edu.utn.frsf.isi.alloatti.ripani.dam.bancog.modelo.Cliente;
import ar.edu.utn.frsf.isi.alloatti.ripani.dam.bancog.modelo.PlazoFijo;

public class MainActivity extends AppCompatActivity {

    private PlazoFijo pf;
    private Cliente cliente;

    private Button btnHacerPlazoFijo;
    private EditText edtMonto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pf = new PlazoFijo(tasas.xml);
        cliente = new Cliente();
    }

    btnHacerPlazoFijo = (Button) findViewById(R.id.btnHacerPF);
    edtMonto = (EditText) findViewById(R.id.edtMonto);
    btnHacerPlazoFijo.setEnabled(false);

    seekDias.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            // actualizar el textview de dias
            // setear los dias en el plazo fijo
            // actualizar el caluclo de los intereses pagados
        }

        public void onStartTrackingTouch(SeekBar seekBar) {}

        public void  onStopTrackingTouch(SeekBar seekBar) {}
    }

    chkAceptaTerminos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b){
            /// RESOLVER logica
        }
    });
}
