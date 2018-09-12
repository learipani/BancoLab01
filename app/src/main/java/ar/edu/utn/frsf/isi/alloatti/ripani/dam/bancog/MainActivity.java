package ar.edu.utn.frsf.isi.alloatti.ripani.dam.bancog;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import ar.edu.utn.frsf.isi.alloatti.ripani.dam.bancog.modelo.Cliente;
import ar.edu.utn.frsf.isi.alloatti.ripani.dam.bancog.modelo.Moneda;
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

        seekDias.setMax(170);
        //seekDias.setMin(10);
        //seekDias

        seekDias.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // actualizar el textview de dias
                lblDiasSeleccionados.setText("Se seleccionaron "+ (i+10) +"dias.");
                // setear los dias en el plazo fijo
                pf.setDias(i+10);
                // actualizar el caluclo de los intereses pagados
                pf.CalcularTasa();
            }

            public void onStartTrackingTouch(SeekBar seekBar) {}

            public void  onStopTrackingTouch(SeekBar seekBar) {}
        });

        tgbtnAccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!pf.getRenovarAutomaticamente()){
                    pf.setRenovarAutomaticamente(true);
                }
                else pf.setRenovarAutomaticamente(false);
            }
        });

        btnHacerPlazoFijo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lblIntereses.setText("");
                lblInfo.setText("");
                StringBuilder erroresNotificacicon= new StringBuilder();
                boolean errorMail,errorCuilCuit,errorMonto, errorDias;
                errorCuilCuit=true;
                errorDias=true;
                errorMail=true;
                errorMonto=true;

                if(edtMail.getText().toString().isEmpty() == false)
                    errorMail = false;
                else
                    erroresNotificacicon.append("El campo mail no debe ser vacío\n");
                if(edtCuilCuit.getText().toString().isEmpty() == false)
                    errorCuilCuit = false;
                else
                    erroresNotificacicon.append("El campo de cuit/cuil no puede ser vacio\n");

                try{
                    if(Double.parseDouble(edtMonto.getText().toString()) > 0)
                        errorMonto = false;
                    else
                        erroresNotificacicon.append("El monto debe ser mayor que 0\n");
                }catch (NumberFormatException ex){
                    erroresNotificacicon.append("Monto debe ser un número\n");
                }

                if(pf.getDias() > 10)
                    errorDias = false;
                else
                    erroresNotificacicon.append("La cantidad de dias de plazo debe ser mayor que 10\n");

                if(!errorCuilCuit && !errorDias && !errorMail && !errorMonto) {
                    pf.setMoneda(rbtnPeso.isChecked()? Moneda.PESO : Moneda.DOLAR);
                    pf.setMonto(Double.parseDouble(edtMonto.getText().toString()));
                    pf.setDias(seekDias.getProgress() + 10);
                    pf.setAvisarVencimiento(tgbtnAccion.isChecked());
                    lblIntereses.setText(pf.getMoneda()==Moneda.DOLAR? "U$D " + pf.intereses(): "AR$ " + pf.intereses());
                    lblInfo.setText("\nEl plazo fijo se realizó correctamente" +
                            "\n PlazoFijo(dias="+ pf.getDias().toString() + ", monto="+pf.getMonto().toString()+
                            ", avisarVencimiento="+swAvisarV.isChecked()+" renovarAutomaticamente= "+
                            pf.getRenovarAutomaticamente()+" moneda="+pf.getMoneda().toString()+")");
                    //lblInfo.setText(pf.toString());
                    lblInfo.setTextColor(Color.BLUE);
                }else{
                    Toast t = new Toast(getApplicationContext());

                    TextView txt = new TextView(MainActivity.this);
                    txt.setTextColor(Color.RED);
                    txt.setText(erroresNotificacicon.toString());

                    t.setDuration(Toast.LENGTH_LONG);
                    t.setView(txt);
                    t.show();
                    //Toast.makeText(MainActivity.this, erroresNotificacicon.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        ckbAceptoTerminos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                btnHacerPlazoFijo.setEnabled(isChecked);
                if(isChecked == false){
                    Toast.makeText(MainActivity.this, "Es obligatorio aceptar las condiciones", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
