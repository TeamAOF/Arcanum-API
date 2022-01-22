package mod.octavo.util;

import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class StreamUtil {
    public static <X extends NbtElement, Z> Stream<Z> streamAndApply(NbtList list, Class<X> filterType, Function<X, Z> applicator){
        return list.stream().filter(filterType::isInstance).map(filterType::cast).map(applicator);
    }

    public static <X, Z> List<X> partialReduce(List<X> in, Function<? super X, ? extends Z> categorizer, BinaryOperator<X> merger){
        return in.stream()
                .collect(Collectors.groupingBy(categorizer)).values().stream()
                .map(elements -> elements.stream().reduce(merger))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static <X> Stream<X> toStream(@Nullable Iterable<X> iterable){
        return iterable != null ? StreamSupport.stream(iterable.spliterator(), false) : Stream.empty();
    }
}
