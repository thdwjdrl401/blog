const App = {
    // API Utilities
    api: {
        async get(url) {
            const response = await fetch(url);
            if (!response.ok) throw new Error('Network response was not ok');
            return response.json();
        },
        async post(url, data) {
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            });
            if (response.status === 401 || response.status === 403) {
                window.location.href = '/login.html';
                throw new Error('Unauthorized');
            }
            if (!response.ok) throw new Error('Request failed');
            return response.json();
        },
        async put(url, data) {
            const response = await fetch(url, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            });
            if (response.status === 401 || response.status === 403) {
                window.location.href = '/login.html';
                throw new Error('Unauthorized');
            }
            if (!response.ok) throw new Error('Request failed');
            return response.json();
        },
        async upload(url, file) {
            const formData = new FormData();
            formData.append('file', file);
            const response = await fetch(url, {
                method: 'POST',
                body: formData
            });
            if (!response.ok) throw new Error('Upload failed');
            return response.json();
        }
    },

    // Format Date
    formatDate(dateString) {
        const date = new Date(dateString);
        return date.toLocaleDateString('ko-KR', {
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        });
    },

    // Auth Helpers
    async login(username, password) {
        try {
            await App.api.post('/api/admin/auth/login', { username, password });
            // Don't redirect here, let the caller handle it.
        } catch (e) {
            throw e; // Propagate error
        }
    },

    async checkAuth() {
        try {
            const user = await App.api.get('/api/admin/auth/me');
            return user;
        } catch (e) {
            return null;
        }
    },

    logout() {
        fetch('/api/admin/auth/logout', { method: 'POST' })
            .then(() => window.location.href = '/');
    }
};
