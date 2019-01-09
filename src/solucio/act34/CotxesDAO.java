package solucio.act34;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author julian
 */
public class CotxesDAO {
    
    // MARCA
    
    public void createMarca(Marca marca) {
        
        new JPATransaction<Object>() {
            @Override
            Object result(EntityManager manager) {
                manager.persist(marca);
                return null;
            }            
        }.result();
    }
    
    public Marca findMarca(String nom) {
        
        return new JPATransaction<Marca>() {
            @Override
            Marca result(EntityManager manager) {
                return localFindMarca(manager, nom);
            }            
        }.result();
    }

    private Marca localFindMarca(EntityManager manager, String nom) {
        
        return manager.createQuery(
                "SELECT m FROM Marca m WHERE marca = :marca", Marca.class)
                .setParameter("marca", nom).getSingleResult();
    }
    
    public boolean deleteMarca(String nom) {
        
        return new JPATransaction<Boolean>() {            
            @Override
            Boolean result(EntityManager manager) {
                
                List<Cotxe> cotxes = localFindCotxesMarca(manager, nom);
                for (Cotxe cotxe : cotxes) {
                    for (Variant variant : cotxe.getVariants()) {
                        manager.remove(variant);
                    }
                    manager.remove(cotxe);
                }

                try {
                    Marca marca = localFindMarca(manager, nom);
                    manager.remove(marca);
                    return true;

                } catch (NoResultException e) {
                    return false;
                }
            }
        }.result();        
    }
    
    public List<Marca> findMarques() {
        
        return new JPATransaction<List<Marca>>() {
            @Override
            List<Marca> result(EntityManager manager) {
                return manager.createQuery(
                    "SELECT m FROM Marca m", Marca.class).getResultList();
            }            
        }.result();
    }
    
    // COTXES
    
    public List<Cotxe> findCotxesMarca(String nom) {
        
        return new JPATransaction<List<Cotxe>>() {
            @Override
            List<Cotxe> result(EntityManager manager) {
                return localFindCotxesMarca(manager, nom);
            }            
        }.result();        
    }
    
    private Cotxe localFindCotxeMarca(EntityManager manager, String marcaStr, String cotxeStr) {
        
        TypedQuery<Cotxe> query = manager.createQuery(
                "SELECT c FROM Cotxe c WHERE c.marca.marca = :marca AND c.model = :model", Cotxe.class);
        query.setParameter("marca", marcaStr);
        query.setParameter("model", cotxeStr);
        Cotxe cotxe = query.getSingleResult();
        return cotxe;
    }

    private List<Cotxe> localFindCotxesMarca(EntityManager manager, String marcaStr) {
        
        TypedQuery<Cotxe> query = manager.createQuery(
                "SELECT c FROM Cotxe c WHERE c.marca.marca = :nom", Cotxe.class);
        query.setParameter("nom", marcaStr);
        List<Cotxe> cotxes = query.getResultList();
        return cotxes;
    }
    
    public Cotxe findCotxe(String marcaStr, String modelStr) {
        
        return new JPATransaction<Cotxe>() {
            @Override
            Cotxe result(EntityManager manager) {
                return localFindCotxeMarca(manager, marcaStr, modelStr);
            }            
        }.result();        
    }
    
    public Cotxe createCotxe(String marcaStr, String modelStr) {
        
        return new JPATransaction<Cotxe>() {            
            @Override
            Cotxe result(EntityManager manager) {
                
                Marca marca = localFindMarca(manager, marcaStr);
                Cotxe cotxe = new Cotxe();
                cotxe.setMarca(marca);
                cotxe.setModel(modelStr);

                manager.persist(cotxe);
                return cotxe;
            }            
        }.result();        
    }
    
    public boolean deleteCotxe(String marcaStr, String modelStr) {
        
        return new JPATransaction<Boolean>() {            
            @Override
            Boolean result(EntityManager manager) {
                
                try {
                    Cotxe cotxe = localFindCotxeMarca(manager, marcaStr, modelStr);
                    for (Variant variant: cotxe.getVariants()) {
                        manager.remove(variant);
                    }                
                    manager.remove(cotxe);
                    return true;

                } catch (NoResultException e) {
                    return false;
                }
            }
            
        }.result();        
    }
    
    // VARIANTS
    
    public List<Variant> findVariantsCotxe(String marca, String model) {
        
        return new JPATransaction<List<Variant>>() {
            @Override
            List<Variant> result(EntityManager manager) {
                TypedQuery<Variant> query = manager.createQuery(
                    "SELECT v FROM Variant v WHERE v.cotxe.model = :model AND v.cotxe.marca.marca = :marca", Variant.class);
                query.setParameter("marca", marca);
                query.setParameter("model", model);

                return query.getResultList();
            }
        }.result();
    }    
    
    public Variant createVariant(String marcaStr, String modelStr, String variantStr) {
        
        return new JPATransaction<Variant>() {
            @Override
            Variant result(EntityManager manager) {
                Cotxe cotxe = localFindCotxeMarca(manager, marcaStr, modelStr);
                Variant variant = new Variant();
                variant.setCotxe(cotxe);
                variant.setVariant(variantStr);

                manager.persist(variant);
                return variant;
            }
            
        }.result();
    }
    
    // UTILS
    
    private static EntityManagerFactory emf;
    
    private EntityManagerFactory getEMF() {
        
        if (CotxesDAO.emf == null) {
            CotxesDAO.emf = Persistence.createEntityManagerFactory("tasques_ms");
        }
        return CotxesDAO.emf;
    }
    
    public static void exit() {
        
        if (CotxesDAO.emf != null) {
            CotxesDAO.emf.close();
        }
    }
    
    private abstract class JPATransaction<T> {
        
        abstract T result(EntityManager manager);
        
        public T result() {
            
            EntityManager manager = getEMF().createEntityManager();
            EntityTransaction transaction = null;

            try {
                transaction = manager.getTransaction();
                transaction.begin();
                
                T value = result(manager);

                transaction.commit();
                return value;

            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }

                throw ex;

            } finally {
                manager.close();
            }
        }
    }
}
