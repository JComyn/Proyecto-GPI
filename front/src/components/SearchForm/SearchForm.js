import React from 'react';
import PropTypes from 'prop-types';
import './styles.css';
import { useOficinas } from 'hooks/useOficinas';


function SearchFormUI({ formData, errors, handleChange, handleSubmit }) {

  const {oficinas, errorOficinas} = useOficinas();

  if(errorOficinas) alert("No hay oficinas en la BBDD");


  
    return (
      <div className="search-form-container">
        <h2 className="search-form-title">Encuentra tu Coche Perfecto</h2>
        <form onSubmit={handleSubmit}>
          <div className="form-grid">
            {/* Pickup Column */}
            <div className="pickup-column">
              <div className="form-group">
                <label htmlFor="pickupOffice">Oficina de Recogida:</label>
                <select 
                  id="pickupOffice" 
                  name="pickupOffice" 
                  value={formData.pickupOffice}
                  onChange={handleChange}
                  className={errors.pickupOffice ? 'error' : ''}
                >
                  <option value="">Seleccione oficina de recogida</option>
                  {oficinas.map(office => (
                    <option key={office.id} value={office.id}>{office.direccion}</option>
                  ))}
                </select>
                {errors.pickupOffice && <p className="error-message">{errors.pickupOffice}</p>}
              </div>
  
              <div className="form-group">
                <label htmlFor="pickupDate">Fecha de Recogida:</label>
                <input 
                  type="date" 
                  id="pickupDate" 
                  name="pickupDate" 
                  value={formData.pickupDate}
                  onChange={handleChange}
                  className={errors.pickupDate ? 'error' : ''}
                />
                {errors.pickupDate && <p className="error-message">{errors.pickupDate}</p>}
              </div>
  
              <div className="form-group">
                <label htmlFor="pickupTime">Hora de Recogida:</label>
                <input 
                  type="time" 
                  id="pickupTime" 
                  name="pickupTime" 
                  value={formData.pickupTime}
                  onChange={handleChange}
                  className={errors.pickupTime ? 'error' : ''}
                />
                {errors.pickupTime && <p className="error-message">{errors.pickupTime}</p>}
              </div>
            </div>
  
            {/* Return Column */}
            <div className="return-column">
              <div className="form-group">
                <label htmlFor="returnOffice">Oficina de Devolución:</label>
                <select 
                  id="returnOffice" 
                  name="returnOffice" 
                  value={formData.returnOffice}
                  onChange={handleChange}
                  className={errors.returnOffice ? 'error' : ''}
                >
                  <option value="">Seleccione oficina de devolución</option>
                  {oficinas.map(office => (
                    <option key={office.id} value={office.id}>{office.direccion}</option>
                  ))}
                </select>
                {errors.returnOffice && <p className="error-message">{errors.returnOffice}</p>}
              </div>
  
              <div className="form-group">
                <label htmlFor="returnDate">Fecha de Devolución:</label>
                <input 
                  type="date" 
                  id="returnDate" 
                  name="returnDate" 
                  value={formData.returnDate}
                  onChange={handleChange}
                  className={errors.returnDate ? 'error' : ''}
                />
                {errors.returnDate && <p className="error-message">{errors.returnDate}</p>}
              </div>
  
              <div className="form-group">
                <label htmlFor="returnTime">Hora de Devolución:</label>
                <input 
                  type="time" 
                  id="returnTime" 
                  name="returnTime" 
                  value={formData.returnTime}
                  onChange={handleChange}
                  className={errors.returnTime ? 'error' : ''}
                />
                {errors.returnTime && <p className="error-message">{errors.returnTime}</p>}
              </div>
            </div>
          </div>
  
          <div className="submit-container">
            <button type="submit" className="submit-button">
              Buscar Coches Disponibles
            </button>
          </div>
        </form>
      </div>
    );
  }

SearchFormUI.propTypes = {
  formData: PropTypes.object.isRequired,
  errors: PropTypes.object.isRequired,
  handleChange: PropTypes.func.isRequired,
  handleSubmit: PropTypes.func.isRequired,
};

export default SearchFormUI;