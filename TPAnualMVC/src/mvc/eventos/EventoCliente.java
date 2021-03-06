package mvc.eventos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import dao.negocio.*;
import mvc.controller.*;
import mvc.view.VistaCliente;

public class EventoCliente implements ActionListener{
	
	
//Se llama a los controladores y la vista del cliente	
	VistaCliente vista;
	ControladorCliente contCliente;
	ControladorDireccion contDireccion;
	ControladorTelefono contTelefono;
	ControladorPasaporte contPasaporte;
	ControladorPasajeroFrecuente contPF;
	ControladorLineaAerea contLA;
	ControladorPais contPais;
	ControladorProvincia contProvincia;
	
	public EventoCliente(VistaCliente vista) {
		this.vista = vista;
		contCliente = new ControladorCliente();
		contDireccion = new ControladorDireccion();
		contTelefono = new ControladorTelefono();
		contPasaporte = new ControladorPasaporte();
		contPF = new ControladorPasajeroFrecuente();
		contLA = new ControladorLineaAerea();
		contPais = new ControladorPais();
		contProvincia = new ControladorProvincia();
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
	//Bot�n atras para que desaparezca la vista actual	
		if(e.getSource()==vista.btnAtras) {
			vista.setVisible(false);
		
	//Alta	
		}else if(e.getSource()==vista.btnAgregar) {
			try {
			
			//Obtiene los datos de los campos	
				String nombre = vista.tf_nombre.getText();
				String apellido = vista.tf_apellido.getText();
				String dni = vista.tf_dni.getText();
				String cuit = vista.tf_cuit.getText();
				String fecha_nac = vista.tf_fecha_nacimiento.getText();
				String email = vista.tf_email.getText();
				
			//Direcci�n	
				String calle = vista.tf_calle.getText();
				String altura = vista.tf_altura.getText();
				String ciudad = vista.tf_ciudad.getText();
				String postal = vista.tf_codigo_postal.getText();
				String provincia = (String) vista.comboProvincia.getSelectedItem();
				String pais = (String) vista.comboPais.getSelectedItem();
				
				
			//Chequea si se ingres� el pa�s y la provincia mediante los campos "Otro"	
				if(provincia.equalsIgnoreCase("Internacional")) {
					if(pais.equalsIgnoreCase("Otro")) {
						contPais.altaPais(new Pais(vista.tf_otroPais.getText()));
						contProvincia.altaProv(new Provincia(vista.tf_otraProvincia.getText()));
						Pais p = contPais.consultarPais(vista.tf_otroPais.getText());
						Provincia prov = contProvincia.consultarPorNombre(vista.tf_otraProvincia.getText());
						Direccion d = new Direccion(calle, altura, ciudad, postal, prov,  p);
						contDireccion.altaDireccion(d);
					}else {
						contProvincia.altaProv(new Provincia(vista.tf_otraProvincia.getText()));
						Provincia prov = contProvincia.consultarPorNombre(vista.tf_otraProvincia.getText());
						Pais p = contPais.consultarPais(pais);
						Direccion d = new Direccion(calle, altura, ciudad, postal, prov,  p);
						contDireccion.altaDireccion(d);
					}	
				}else {
					Provincia prov = contProvincia.consultarPorNombre(provincia);
					Pais p = contPais.consultarPais(pais);
					
					Direccion d = new Direccion(calle, altura, ciudad, postal, prov,  p);
					contDireccion.altaDireccion(d);
				}	
				
			//Tel�fono
				String celular = vista.tf_celular.getText();
				String personal = vista.tf_personal.getText();
				String laboral = vista.tf_laboral.getText();
				Telefono t = new Telefono(celular, personal, laboral);
				contTelefono.altaTelefono(t);
				
				
			//Pasaporte
				String numero = vista.tf_numeroPasaporte.getText();
				String autoridad = vista.tf_autoridadEmision.getText();
				String fecha_emision = vista.tf_fechaEmision.getText();
				String fecha_venc = vista.tf_fechaVencimiento.getText();
				String pais_emision = (String) vista.comboPaisEmision.getSelectedItem();
				Pais paisEmision = contPais.consultarPais(pais_emision);
				Pasaporte pasaporte = new Pasaporte(numero, autoridad, fecha_emision, fecha_venc, paisEmision);
				contPasaporte.altaPasaporte(pasaporte);
				
			//Pasajero frecuente
				String categoria = vista.tf_categoria.getText();
				String numeroPF = vista.tf_numeroPF.getText();
				Alianza alianza = (Alianza) vista.comboAlianza.getSelectedItem();
				String nombre_aerolinea = (String) vista.comboAerolinea.getSelectedItem();
				Aerolinea aerolinea = contLA.consultaPorNombre(nombre_aerolinea);
				PasajeroFrecuente pf = new PasajeroFrecuente(categoria, numeroPF, alianza, aerolinea);
				contPF.altaPasajFrecuente(pf);
				
				Cliente c = new Cliente(nombre, apellido, dni, cuit, fecha_nac, email,contDireccion.obtenerUltimo(), contTelefono.obtenerUltimo(), contPasaporte.obtenerUltimo(), contPF.obtenerUltimo());
				
			//Se realiza la alta y se muestra por pantalla el cliente ingresado	
				if(contCliente.altaCliente(c)) {
					JOptionPane.showMessageDialog(null, "Cliente agregado", "Alta de cliente", JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, "Error al agregar al cliente", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}catch(NullPointerException np) {
				JOptionPane.showMessageDialog(null, "Compruebe que no queden campos por completar", "Error", JOptionPane.ERROR_MESSAGE);
			}catch(Exception ex) {
				JOptionPane.showMessageDialog(null,"Compruebe que est�n bien todos los datos", "Error", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			
			}
			
	//Consulta	
		}else if(e.getSource()==this.vista.btnConsultar) {
			try {
				//Se consulta el cliente mediante el campo id	
				Integer id = Integer.parseInt(this.vista.tf_id.getText());
				
				Cliente c = contCliente.consultaPorId(id);
				
			//Se setean los datos del cliente mediante las consultas hechas	
				vista.tf_nombre.setText(c.getNombre());
				vista.tf_apellido.setText(c.getApellido());
				vista.tf_dni.setText(c.getDni());
				vista.tf_cuit.setText(c.getCuit_cuil());
				vista.tf_fecha_nacimiento.setText(c.getFecha_nacimiento());
				vista.tf_email.setText(c.getEmail());
				
				vista.lbl_id_direccion.setText(c.getdireccion().getId_direccion().toString());
				vista.tf_calle.setText(c.getdireccion().getCalle());
				vista.tf_altura.setText(c.getdireccion().getAltura());
				vista.tf_ciudad.setText(c.getdireccion().getCiudad());
				vista.tf_codigo_postal.setText(c.getdireccion().getCodigoPostal());
				vista.comboProvincia.setSelectedItem(c.getdireccion().getProvincia().getNombreProvincia());
				vista.comboPais.setSelectedItem(c.getdireccion().getPais().getNombrePais());
				
				vista.lbl_id_telefono.setText(c.gettelefono().getId_Telefono().toString());
				vista.tf_celular.setText(c.gettelefono().getCelular());
				vista.tf_personal.setText(c.gettelefono().getPersona());
				vista.tf_laboral.setText(c.gettelefono().getLaboral());
				
				vista.lbl_id_pasaporte.setText(c.getpasaporte().getId_Pasaporte().toString());
				vista.tf_numeroPasaporte.setText(c.getpasaporte().getNumero());
				vista.tf_autoridadEmision.setText(c.getpasaporte().getAutoridadEmision());
				vista.tf_fechaEmision.setText(c.getpasaporte().getFechaEmision());
				vista.tf_fechaVencimiento.setText(c.getpasaporte().getFechaVencimiento());
				vista.comboPaisEmision.setSelectedItem(c.getpasaporte().getPaisEmision().getNombrePais());
				
				vista.lbl_id_pasajeroFrecuente.setText(c.getpasajeroFrecuente().getId_pasajeroFrecuente().toString());
				vista.tf_categoria.setText(c.getpasajeroFrecuente().getCategoria());
				vista.tf_numeroPF.setText(c.getpasajeroFrecuente().getNumero());
				vista.comboAlianza.setSelectedItem(c.getpasajeroFrecuente().getAlianza());
				vista.comboAerolinea.setSelectedItem(c.getpasajeroFrecuente().getAerolinea().getNombre());
				
			}catch(NullPointerException ex) {
				JOptionPane.showMessageDialog(null,"Compruebe que exista el id ingresado", "Error", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}catch(NumberFormatException ex) {
				JOptionPane.showMessageDialog(null,"Los id son numeros enteros", "Error", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		
			
			
	//Eliminaci�n	
		}else if(e.getSource()==vista.btnEliminar) {
		//Confirma la eliminaci�n	
			try {
				int resultado = JOptionPane.showConfirmDialog(null, "Se eliminar� el registro", "Warning", 
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				if(resultado == 0) {
					
				//Se dan de baja todos los datos del cliente						
					contDireccion.bajaDireccion(vista.lbl_id_direccion.getText());
					contTelefono.bajaTelefono(vista.lbl_id_telefono.getText());
					contPasaporte.bajaPasaporte(vista.lbl_id_pasaporte.getText());
					contPF.bajaPasajFrecuente(vista.lbl_id_pasajeroFrecuente.getText());
					
					if(contCliente.bajaCliente(vista.tf_id.getText())) {
						JOptionPane.showMessageDialog(null, "Cliente eliminado", "Baja cliente", JOptionPane.INFORMATION_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(null, "Error al eliminar", "Error", JOptionPane.ERROR_MESSAGE);
					}
	
				}
			}catch(NullPointerException np) {
				JOptionPane.showMessageDialog(null, "Compruebe que exista el id ingresado", "Error", JOptionPane.ERROR_MESSAGE);
			}catch(NumberFormatException ex) {
				JOptionPane.showMessageDialog(null,"Los id son numeros enteros", "Error", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "Error en la base de datos");
				ex.printStackTrace();
			}
	
	//Modificaci�n		
		}else if(e.getSource()==vista.btnModificar) {
			try {
				
			//Obtiene los campos completados en la consulta y realiza la modificaci�n	
				Integer id = Integer.parseInt(vista.tf_id.getText());
				String nombre = vista.tf_nombre.getText();
				String apellido = vista.tf_apellido.getText();
				String dni = vista.tf_dni.getText();
				String cuit = vista.tf_cuit.getText();
				String fecha_nac = vista.tf_fecha_nacimiento.getText();
				String email = vista.tf_email.getText();
				
			//Direcci�n	
				Direccion d;
				Integer id_direccion = Integer.parseInt(vista.lbl_id_direccion.getText());
				String calle = vista.tf_calle.getText();
				String altura = vista.tf_altura.getText();
				String ciudad = vista.tf_ciudad.getText();
				String postal = vista.tf_codigo_postal.getText();
				String provincia = (String) vista.comboProvincia.getSelectedItem();
				String pais = (String) vista.comboPais.getSelectedItem();
				
				
			//Chequea si se ingres� el pa�s y la provincia mediante los campos "Otro"	
				if(provincia.equalsIgnoreCase("Internacional")) {
					if(pais.equalsIgnoreCase("Otro")) {
						contPais.altaPais(new Pais(vista.tf_otroPais.getText()));
						contProvincia.altaProv(new Provincia(vista.tf_otraProvincia.getText()));
						Pais p = contPais.consultarPais(vista.tf_otroPais.getText());
						Provincia prov = contProvincia.consultarPorNombre(vista.tf_otraProvincia.getText());
						d = new Direccion(id_direccion, calle, altura, ciudad, postal, prov,  p);
						contDireccion.modDireccion(d);
					}else {
						contProvincia.altaProv(new Provincia(vista.tf_otraProvincia.getText()));
						Provincia prov = contProvincia.consultarPorNombre(vista.tf_otraProvincia.getText());
						Pais p = contPais.consultarPais(pais);
						d = new Direccion(id_direccion, calle, altura, ciudad, postal, prov,  p);
						contDireccion.modDireccion(d);
					}	
				}else {
					Provincia prov = contProvincia.consultarPorNombre(provincia);
					Pais p = contPais.consultarPais(pais);
					
					d = new Direccion(id_direccion, calle, altura, ciudad, postal, prov,  p);
					contDireccion.modDireccion(d);
				}	
				
				
			//Tel�fono
				Integer id_telefono = Integer.parseInt(vista.lbl_id_telefono.getText());
				String celular = vista.tf_celular.getText();
				String personal = vista.tf_personal.getText();
				String laboral = vista.tf_laboral.getText();
				Telefono t = new Telefono(id_telefono, celular, personal, laboral);
				contTelefono.modTelefono(t);
				
				
			//Pasaporte
				Integer id_pasaporte = Integer.parseInt(vista.lbl_id_pasaporte.getText());
				String numero = vista.tf_numeroPasaporte.getText();
				String autoridad = vista.tf_autoridadEmision.getText();
				String fecha_emision = vista.tf_fechaEmision.getText();
				String fecha_venc = vista.tf_fechaVencimiento.getText();
				String pais_emision = (String) vista.comboPaisEmision.getSelectedItem();
				Pais paisEmision = contPais.consultarPais(pais_emision);
				Pasaporte pasaporte = new Pasaporte(id_pasaporte, numero, autoridad, fecha_emision, fecha_venc, paisEmision);
				contPasaporte.modPasaporte(pasaporte);
				
			//Pasajero frecuente
				Integer id_pf = Integer.parseInt(vista.lbl_id_pasajeroFrecuente.getText());
				String categoria = vista.tf_categoria.getText();
				String numeroPF = vista.tf_numeroPF.getText();
				Alianza alianza = (Alianza) vista.comboAlianza.getSelectedItem();
				String nombre_aerolinea = (String) vista.comboAerolinea.getSelectedItem();
				Aerolinea aerolinea = contLA.consultaPorNombre(nombre_aerolinea);
				PasajeroFrecuente pf = new PasajeroFrecuente(id_pf, categoria, numeroPF, alianza, aerolinea);
				contPF.modPasajFrecuente(pf);
				
				Cliente c = new Cliente(id, nombre, apellido, dni, cuit, fecha_nac, email, d, t, pasaporte, pf);
				
				if(contCliente.modificarCliente(c)) {
					JOptionPane.showMessageDialog(null, "Cliente modificado", "Modificaci�n de cliente", JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, "Error al modificar al cliente", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
								
			}catch(NullPointerException np) {
				JOptionPane.showMessageDialog(null, "Compruebe que no queden campos por completar", "Error", JOptionPane.ERROR_MESSAGE);
				np.printStackTrace();
			}catch(Exception ex) {
				JOptionPane.showMessageDialog(null,"Compruebe que est�n bien todos los datos", "Error", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			
			}
		}
		
		
	}
	
	
	
	

}
