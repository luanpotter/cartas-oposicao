package xyz.luan.monstros.ws;

import xyz.luan.monstros.domain.company.Company;
import xyz.luan.monstros.domain.user.User;

public class AuthHolder {
	public static final ThreadLocal<Company> company = new ThreadLocal<>();
	public static final ThreadLocal<User> user = new ThreadLocal<>();
}
