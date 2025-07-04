import React, { useState } from 'react';
import PropTypes from 'prop-types';
import SearchFormUI from './SearchForm';

function SearchForm({ onSearch, formData, setFormData }) {
  
  const [errors, setErrors] = useState({});

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
    
    if (errors[name]) {
      setErrors({
        ...errors,
        [name]: null
      });
    }
  };

  const validateForm = () => {
    const newErrors = {};
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    
    // Mensajes de error en español
    const errorMessages = {
      pickupOffice: "La oficina de recogida es obligatoria",
      returnOffice: "La oficina de devolución es obligatoria",
      pickupDate: "La fecha de recogida es obligatoria",
      pickupTime: "La hora de recogida es obligatoria",
      returnDate: "La fecha de devolución es obligatoria",
      returnTime: "La hora de devolución es obligatoria"
    };
    
    Object.keys(formData).forEach(key => {
      if (!formData[key]) {
        newErrors[key] = errorMessages[key];
      }
    });

    if (formData.pickupDate) {
      const pickupDate = new Date(formData.pickupDate);
      if (pickupDate < today) {
        newErrors.pickupDate = "La fecha de recogida debe ser en el futuro";
      }
    }

    if (formData.pickupDate && formData.returnDate) {
      const pickupDate = new Date(formData.pickupDate);
      const returnDate = new Date(formData.returnDate);
      if (returnDate < pickupDate) {
        newErrors.returnDate = "La fecha de devolución debe ser posterior a la de recogida";
      }
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (validateForm()) {
      onSearch(formData);
    }
  };

  return (
    <SearchFormUI
      formData={formData}
      errors={errors}
      handleChange={handleChange}
      handleSubmit={handleSubmit}
    />
  );
}

SearchForm.propTypes = {
  onSearch: PropTypes.func.isRequired,
  formData: PropTypes.object.isRequired,
  setFormData: PropTypes.func.isRequired
};

export default SearchForm;