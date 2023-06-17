package br.com.uoutec.community.ediacaran.persistence.registry;

import java.util.List;

import br.com.uoutec.community.ediacaran.persistence.entity.Language;
import br.com.uoutec.community.ediacaran.plugins.PublicBean;
import br.com.uoutec.entity.registry.Registry;

public interface LanguageRegistry extends Registry, PublicBean{

	public static final String PERMISSION_PREFIX = "app.registry.language.";
	
	void registerLanguage(Language e) throws LanguageRegistryException;

	void removeLanguage(Language e) throws LanguageRegistryException;

	Language getLanguage(int id) throws LanguageRegistryException;

	Language getLanguageByIso6391(String value) throws LanguageRegistryException;
	
	Language getLanguageByIso6392t(String value) throws LanguageRegistryException;
	
	List<Language> getAll() throws LanguageRegistryException;

	void flush();
	
}
