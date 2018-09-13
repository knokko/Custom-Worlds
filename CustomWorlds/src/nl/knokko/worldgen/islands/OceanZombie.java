package nl.knokko.worldgen.islands;

import java.lang.reflect.Field;
import net.minecraft.server.v1_12_R1.EntityZombie;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;

public class OceanZombie extends EntityZombie {

	public OceanZombie(org.bukkit.World world) {
		super(((CraftWorld)world).getHandle());
	}
	
	@Override
	public void B_(){
		//60 times per second
		//This code makes sure that monsters die if the difficulty is peaceful
		super.B_();
	}
	
	@Override
	public void aE(){
		super.aE();//something with fall distance
		System.out.println("OceanZombie.aE()");
	}
	
	@Override
	public void as(){
		//20 times per second --> assuming this is the update method
		System.out.println("OceanZombie.as()");
		super.as();
	}
	
	@Override
	public void ba(){
		super.ba();//also something with fall distance
		System.out.println("OceanZombie.ba()");
	}
	
	@SuppressWarnings("rawtypes")
	public static Object getPrivateField(String fieldName, Class clazz, Object object)
    {
        Field field;
        Object o = null;

        try
        {
            field = clazz.getDeclaredField(fieldName);

            field.setAccessible(true);

            o = field.get(object);
        }
        catch(NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        catch(IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return o;
    }
}
