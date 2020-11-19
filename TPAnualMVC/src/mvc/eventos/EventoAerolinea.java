package mvc.eventos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import dao.negocio.Aerolinea;
import dao.negocio.Alianza;
import mvc.controller.*;
import mvc.view.*;

public class EventoAerolinea implements ActionListener{

	ControladorLineaAerea contLA;
	VistaAerolinea vista;
	
	public EventoAerolinea(VistaAerolinea vista) {
		this.vista = vista;
		contLA = new ControladorLineaAerea();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if(e.getSource()== vista.btnAtras) {
				vista.setVisible(false);
			}else if(e.getSource()==vista.btnAltaAerolinea) {
				String nombre = vista.textFieldNombre.getText();
				Object alianza = vista.comboBoxAlianza.getSelectedItem();
				Aerolinea a = new Aerolinea(nombre, Alianza.valueOf((String) alianza));
				contLA.altaLineaAerea(a);
			}else if(e.getSource()==vista.btnConsultaAerolinea) {
				String id = vista.ModtextFieldID.getText();
				Aerolinea a =contLA.consultarLineaAerea(id);
				
				vista.ModtextFieldNombre.setText(a.getNombre());
				vista.ModcomboBoxAlianza.setSelectedItem(a.getAlianza());
			}else if(e.getSource()==vista.btnModificar) {
				String id = vista.ModtextFieldID.getText();
				String nombre = vista.ModtextFieldNombre.getText();
				String alianza = (String) vista.ModcomboBoxAlianza.getSelectedItem();
				
				Aerolinea a = new Aerolinea(Integer.parseInt(id), nombre, Alianza.valueOf(alianza));
				
				contLA.modLineaAerea(a);
			}else if(e.getSource()==vista.btnEliminarAerolinea) {
				int input =JOptionPane.showConfirmDialog(null, "Se eliminar� la aerol�nea", "WARNING", JOptionPane.OK_CANCEL_OPTION);
				if(input == 0) {
					contLA.bajaLineaAerea(vista.ModtextFieldID.getText());
				}
			}
		}catch(Exception ex) {
			//JOptionPane.showMessageDialog(null, "Compruebe que los campos sean correctos", "Error", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
		
		
	}
	
	

}