package com.familia.gastos;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.getcapacitor.BridgeActivity;

/**
 * En Android 15 o más nuevo las apps se dibujan por debajo de la barra de estado (hora, señal)
 * y de la barra de navegación. Acá se reserva ese espacio para que la app quede entre ambas.
 * Arriba se pinta del azul del encabezado, abajo del color de fondo de la app.
 */
public class MainActivity extends BridgeActivity {
    private static final int AZUL = 0xFF2563EB;   // mismo azul del encabezado (--azul)
    private static final int FONDO = 0xFFF4F6FB;  // mismo fondo de la app (--fondo)

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        WindowInsetsControllerCompat controles =
                WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        controles.setAppearanceLightStatusBars(false);     // iconos claros sobre el azul
        controles.setAppearanceLightNavigationBars(true);  // iconos oscuros sobre el fondo claro

        pintarFondo(0);

        View contenido = findViewById(android.R.id.content);
        ViewCompat.setOnApplyWindowInsetsListener(contenido, (vista, insets) -> {
            Insets barras = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            Insets teclado = insets.getInsets(WindowInsetsCompat.Type.ime());
            vista.setPadding(
                    barras.left,
                    barras.top,
                    barras.right,
                    Math.max(barras.bottom, teclado.bottom));
            pintarFondo(barras.top);
            return WindowInsetsCompat.CONSUMED;
        });
    }

    private void pintarFondo(int altoBarraEstado) {
        LayerDrawable fondo = new LayerDrawable(new Drawable[]{
                new ColorDrawable(FONDO),
                new ColorDrawable(AZUL)
        });
        fondo.setLayerGravity(1, Gravity.TOP | Gravity.FILL_HORIZONTAL);
        fondo.setLayerHeight(1, altoBarraEstado);
        getWindow().getDecorView().setBackground(fondo);
    }
}
