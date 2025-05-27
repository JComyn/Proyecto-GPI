import { useState, useEffect } from "react";
import { getFlota } from "services/flotaService";

export const useFlota = () => {
  const [flota, setFlota] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchFlota = async () => {
      try {
        const data = await getFlota();
        setFlota(data);
      } catch (err) {
        setError(err);
      }
    };

    fetchFlota();
  }, []);

  return { flota, error };
};
