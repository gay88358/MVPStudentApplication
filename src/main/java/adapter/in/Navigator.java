package adapter.in;

public interface Navigator {
    <T extends NavigableView> void navigateTo(Class<T> viewClass, Object model);
}
