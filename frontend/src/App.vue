<template>
  <q-layout view="hHh lpR fFf">
    <q-header v-if="currentUserIsLoggedIn" class="topNav" elevated>
      <q-toolbar>
        <router-link to="/">
          <img src="img/mainlogorev.png" class="toplogo"
        /></router-link>
        <q-toolbar-title> TOURNET ERP </q-toolbar-title>
        <q-btn
          aria-label="Home"
          dense
          flat
          icon="house"
          round
          @click="$router.push('/')"
        />
        <q-btn
          aria-label="Menu"
          dense
          flat
          icon="menu"
          round
          @click="openNavigationDrawer = !openNavigationDrawer"
        />
        <div class="text-grey-5">Ver {{ version }}</div>
      </q-toolbar>
    </q-header>
    <navigation-drawer
      v-if="currentUserIsLoggedIn"
      v-model="openNavigationDrawer"
    />
    <q-btn
      v-if="currentUserIsLoggedIn"
      class="drawer-toggle-button"
      :style="{ left: drawerButtonPosition, transition: 'left 0.1s' }"
      icon="menu"
      dense
      @click="openNavigationDrawer = !openNavigationDrawer"
    />
    <q-page-container style="background-color: #f0eeee">
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script lang="ts">
import { defineComponent, ref, watch } from 'vue';
import store from 'src/store';
import NavigationDrawer from 'src/components/navigation/NavigationDrawer.vue';
import router from 'src/router';

export default defineComponent({
  name: 'App',
  components: { NavigationDrawer },
  setup() {
    let openNavigationDrawer = ref(false);
    const version = ref<string>('');
    const currentUserIsLoggedIn = ref(false);

    if (store.state.currentUser) {
      watch(
        () => store.state.currentUser,
        (newVal) => {
          if (newVal) {
            if (newVal.username != '') {
              currentUserIsLoggedIn.value = true;
            }
          } else {
            currentUserIsLoggedIn.value = false;
          }
        },
        { immediate: true }
      );
    } else {
      router.push({ path: '/Login' });
    }
    return {
      openNavigationDrawer,
      currentUserIsLoggedIn,
      version,
    };
  },
  computed: {
    drawerButtonPosition() {
      return this.openNavigationDrawer ? '240px' : '0px';
    },
  },
});
</script>
<style lang="scss">
.topNav {
  background-image: url('src/assets/top_main.png');
  font-family: Raleway;
  font-weight: 500;
  color: #f0eeee;
  padding: 0.5rem;
}
.toplogo {
  height: 2.4rem;
  margin-right: 0.8rem;
}
.part_title {
  font-size: 22px;
  color: #000;
  font-weight: normal;
  font-family: Raleway;
  font-weight: bold;
}

.fieldset {
  border: 1px solid #ddd;
  background-color: #faf9f9;
  border-top: none;
  padding: 0.5em;
  margin: 0.5em 2px;
  margin-bottom: 1rem;
}

.fieldset > h1 {
  font-size: 1em;
  font-weight: bold;
  margin: -1em -0.5em 0;
}

.fieldset > h1 > span {
  float: left;
}

.fieldset > h1:before {
  border-top: 1px solid #ddd;
  content: ' ';
  float: left;
  margin: 0.5em 2px 0 -1px;
  width: 0.75em;
}

.fieldset > h1:after {
  border-top: 1px solid #ddd;
  content: ' ';
  display: block;
  height: 1.5em;
  left: 2px;
  margin: 0 1px 0 0;
  overflow: hidden;
  position: relative;
  top: 0.5em;
}
.fieldset02 {
  border: 1px solid #ddd;
  border-top: none;
  padding: 0.5em;
  margin: 1em 2px;
}

.fieldset02 > h1 {
  font-size: 1em;
  font-weight: bold;
  margin: -1em -0.5em 0;
}

.fieldset02 > h1 > span {
  float: left;
  height: 1em;
  margin: -2.9em 0.5em 0;
}

.fieldset02 > h1:before {
  border-top: 1px solid #ddd;
  content: ' ';
  float: left;
  margin: 0.5em 2px 0 -1px;
  width: 0.9em;
}

.fieldset02 > h1:after {
  border-top: 1px solid #ddd;
  content: '';
  display: block;
  height: 1.2em;
  left: 2px;
  margin: 0 1px 0 11.2em;
  overflow: hidden;
  position: relative;
  top: 0.5em;
}

.fieldset03 {
  border: none;
  padding: 0.5em;
  margin: 0.5em 2px;
}

.fieldset03 > h1 {
  font-size: 1em;
  font-weight: bold;
  margin: -1em -0.5em 0;
}

.fieldset03 > h1 > span {
  float: left;
  height: 1em;
  margin: -2.9em 0.5em 0;
}

.fieldset03 > h1:before {
  border-top: 1px solid #ddd;
  content: ' ';
  float: left;
  margin: 0.5em 2px 0 -1px;
  width: 0.9em;
}

.fieldset03 > h1:after {
  border-top: 1px solid #ddd;
  content: '';
  display: block;
  height: 1.2em;
  left: 2px;
  margin: 0 1px 0 9em;
  overflow: hidden;
  position: relative;
  top: 0.5em;
}

.drawer-toggle-button {
  position: fixed;
  left: 0px;
  top: 50%;
  font-size: 10px;
  transform: translateY(-50%);
  background-color: rgb(255, 255, 255);
  border-top: 1px solid #3f3f3f;
  border-right: 1px solid #3f3f3f;
  border-bottom: 1px solid #3f3f3f;
  z-index: 2;
  width: 25px;
  height: 30px;
  border-radius: 0 50% 50% 0; /* Make the right of the button circular */
  overflow: hidden; /* Hide the bottom half of the button */
}
</style>
