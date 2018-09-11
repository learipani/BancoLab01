package ar.edu.utn.frsf.isi.alloatti.ripani.dam.bancog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import ar.edu.utn.frsf.isi.alloatti.ripani.dam.bancog.modelo.Cliente;
import ar.edu.utn.frsf.isi.alloatti.ripani.dam.bancog.modelo.PlazoFijo;

public class MainActivity extends AppCompatActivity {

    private PlazoFijo pf;
    private Cliente cliente;

    private EditText edtMail;
    private EditText edtCuilCuit;
    private RadioGroup rdgMoneda;
    private RadioButton rbtnDolar;
    private RadioButton rbtnPeso;
    private EditText edtMonto;
    private SeekBar seekDias;
    private TextView lblDiasSeleccionados;
    private TextView lblIntereses;
    private Switch swAvisarV;
    private ToggleButton tgbtnAccion;
    private CheckBox ckbAceptoTerminos;
    private Button btnHacerPlazoFijo;
    private TextView lblInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pf = new PlazoFijo(getResources().getStringArray(R.array.tasas));
        cliente = new Cliente();

        edtMail = (EditText) findViewById(R.id.txtMail);
        edtCuilCuit = (EditText) findViewById(R.id.txtNroCuitCuil);
        rdgMoneda = (RadioGroup) findViewById(R.id.rgMoneda);
        rbtnDolar = (RadioButton) findViewById(R.id.rbtnDolar);
        rbtnPeso = (RadioButton) findViewById(R.id.rbtnPeso);
        edtMonto = (EditText) findViewById(R.id.txtMonto);
        seekDias = (SeekBar) findViewById(R.id.skBarDias);
        lblDiasSeleccionados = (TextView) findViewById(R.id.lblDiasSelecc);
        lblIntereses = (TextView) findViewById(R.id.lblIntereses);
        swAvisarV = (Switch) findViewById(R.id.swAvisar);
        tgbtnAccion = (ToggleButton) findViewById(R.id.tgbtnAccion);
        ckbAceptoTerminos = (CheckBox) findViewById(R.id.ckBoxATyC);
        btnHacerPlazoFijo = (Button) findViewById(R.id.btnPlazoFijo);
        lblInfo = (TextView) findViewById(R.id.lblInfo);

        btnHacerPlazoFijo.setEnabled(false);

        seekDias.setMax(180);
        //seekDias.setMin(10);

        seekDias.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // actualizar el textview de dias
                lblDiasSeleccionados.setText(seekDias.getProgress());
                // setear los dias en el plazo fijo
                pf.setDias(seekDias.getProgress());
                // actualizar el caluclo de los intereses pagados
                pf.CalcularTasa();
            }

            public void onStartTrackingTouch(SeekBar seekBar) {}

            public void  onStopTrackingTouch(SeekBar seekBar) {}
        });

        ckbAceptoTerminos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b){
                /// RESOLVER logica
            }
        });
    }
}
