package cn.demo.classmetadata;

import org.springframework.core.type.StandardClassMetadata;

import java.util.HashMap;
import java.util.Map;

public class CacheStandardClassMetadata extends StandardClassMetadata {
	private Map<String,Object> cache = new HashMap<>();
	public CacheStandardClassMetadata(Class<?> introspectedClass) {
		super(introspectedClass);
	}

	@Override
	public boolean isInterface() {
		Boolean  rs = (Boolean) cache.get("isInterface");
		if (rs!= null) {
			return rs;
		}
		boolean anInterface = super.isInterface();
		cache.put("isInterface",anInterface);
		return anInterface;
	}

	@Override
	public boolean isConcrete() {
		return super.isConcrete();
	}

	@Override
	public boolean hasEnclosingClass() {
		return super.hasEnclosingClass();
	}

	@Override
	public boolean hasSuperClass() {
		return super.hasSuperClass();
	}
}
