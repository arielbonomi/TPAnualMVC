package mvc.controller;

import java.util.List;

import Factory.Factory;
import dao.Interfaces.*;
import dao.negocio.*;

public class ControladorAeropuerto {
	
	AeropuertoDAO aeropDAO;

	public ControladorAeropuerto() {
		new Factory();
		this.aeropDAO = Factory.getAeropuertoDaoImplMysql();
	}
	
	public void altaAeropuerto(Aeropuerto a){
		aeropDAO.altaAeropuerto(a);
	}
	
	public void bajaAeropuerto(String id) {
		aeropDAO.bajaAeropuerto(id);
	}
	
	public Aeropuerto consultarAeropuerto(Integer id) {
		return aeropDAO.getAeropuerto(id);
	}
	
	public List<String> obtenerNombres(){
		return aeropDAO.obtenerCodigos();
	}
	
	public Aeropuerto consultaPorCodigo(String codigo) {
		return aeropDAO.consultaPorCodigo(codigo);
	}

}