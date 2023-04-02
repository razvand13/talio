/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package server;

import com.google.inject.Injector;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Builder;
import javafx.util.BuilderFactory;
import javafx.util.Callback;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class MyFXML {

    private Injector injector;

    /**
     *
     * @param injector
     */
    public MyFXML(Injector injector) {
        this.injector = injector;
    }

    /**
     *
     * @param c
     * @param parts
     * @return Pair<>(ctrl, parent)
     * @param <T>
     */
    public <T> Pair<T, Parent> load(Class<T> c, String... parts) {
        try {
            var loader = new FXMLLoader(getLocation(parts), null,
                    null, new MyFactory(), StandardCharsets.UTF_8);
            Parent parent = loader.load();
            T ctrl = loader.getController();
            return new Pair<>(ctrl, parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param parts
     * @return
     */
    private URL getLocation(String... parts) {
        var path = Path.of("", parts).toString();
        return MyFXML.class.getClassLoader().getResource(path);
    }

    private class MyFactory implements BuilderFactory, Callback<Class<?>, Object> {
        /**
         *
         * @param type the given type or null
         *
         * @return
         */
        @Override
        @SuppressWarnings("rawtypes")
        public Builder<?> getBuilder(Class<?> type) {
            return new Builder() {
                /**
                 *
                 * @return
                 */
                @Override
                public Object build() {
                    return injector.getInstance(type);
                }
            };
        }

        /**
         *
         * @param type The single argument upon which the returned value should be
         *      determined.
         * @return
         */
        @Override
        public Object call(Class<?> type) {
            return injector.getInstance(type);
        }
    }
}