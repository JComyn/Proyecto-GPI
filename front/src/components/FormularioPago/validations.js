/**
 * Validates if the card number has exactly 16 digits after removing spaces
 */
export const validateCardNumber = (cardNumber) => {
    const digitsOnly = cardNumber.replace(/\s/g, "");
    return digitsOnly.length === 16 && /^\d+$/.test(digitsOnly);
  };
  
  /**
   * Formats a card number with spaces after every 4 digits
   */
  export const formatCardNumber = (cardNumber) => {
    // Remove all non-digit characters
    const digitsOnly = cardNumber.replace(/\D/g, "");
    
    // Insert a space after every 4 digits
    let formattedNumber = "";
    for (let i = t= 0; i < digitsOnly.length; i++) {
      if (i > 0 && i % 4 === 0) {
        formattedNumber += " ";
      }
      formattedNumber += digitsOnly[i];
    }
    
    return formattedNumber;
  };