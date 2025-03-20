import React, { useState } from 'react';
import PropTypes from 'prop-types';
import SearchFormUI from './SearchForm';

function SearchForm({ onSearch }) {
  const [formData, setFormData] = useState({
    pickupOffice: "",
    returnOffice: "",
    pickupDate: "",
    pickupTime: "",
    returnDate: "",
    returnTime: ""
  });
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
    
    Object.keys(formData).forEach(key => {
      if (!formData[key]) {
        newErrors[key] = `${key} is required`;
      }
    });

    if (formData.pickupDate) {
      const pickupDate = new Date(formData.pickupDate);
      if (pickupDate < today) {
        newErrors.pickupDate = "Pickup date must be in the future";
      }
    }

    if (formData.pickupDate && formData.returnDate) {
      const pickupDate = new Date(formData.pickupDate);
      const returnDate = new Date(formData.returnDate);
      if (returnDate < pickupDate) {
        newErrors.returnDate = "Return date must be after pickup date";
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
};

export default SearchForm;