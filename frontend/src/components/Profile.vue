<template>
  <div class="q-pa-md row items-start q-gutter-md">
    <q-card dark bordered class="bg-grey-9 my-card">
      <q-card-section>
        {{currentUser.username}}
      </q-card-section>

      <q-separator dark inset />

      <q-card-section>
        <img
          id="profile-img"
          src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
        />
        <p>
          <strong>토큰(Token):</strong>
          {{currentUser.accessToken.substring(0, 20)}} ... {{currentUser.accessToken.substr(currentUser.accessToken.length - 20)}}
        </p>
        <p>
          <strong>이메일(Email):</strong>
          {{currentUser.empEmail}}
        </p>
        <strong>권한(Authorities):</strong>
        <ul>
          <li v-for="role in currentUser.roles" :key="role">{{role}}</li>
        </ul>
      </q-card-section>
    </q-card>


  </div>
</template>

<script>
export default {
  name: 'ProfileTN',
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    }
  },
  mounted() {
    if (!this.currentUser) {
      this.$router.push('/login');
    }
  }
};
</script>
